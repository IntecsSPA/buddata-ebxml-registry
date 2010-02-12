/*
 * Project: Buddata ebXML RegRep
 * Class: RegistryHTTPServlet.java
 * Copyright (C) 2009
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package be.kzen.ergorr.interfaces.soap.security;

import be.kzen.ergorr.commons.CommonProperties;
import com.sun.org.apache.xml.internal.security.utils.RFC2253Parser;
import com.sun.xml.wss.impl.callback.*;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.Subject;

/**
 * SecurityEnvironmentHandler is essentially a CallbackHandler which is used by the XWS-Security
 * run-time to obtain the private-keys, certificates, symmetric keys, etc. to be used in the signing
 * and encryption/decryption operations from the application.
 *
 * Later when different types of key stores have to be supported (e.g LDAP), the KeyStore part should
 * be refactored out of this class and used behind a generic interface.
 *
 * @author Yaman Ustuntas
 */
public class SecurityCallbackHandler implements CallbackHandler {

    private static Logger logger = Logger.getLogger(SecurityCallbackHandler.class.getName());
    private static final String REQUESTER_SERIAL = "requester.serial";
    private static final String REQUESTER_ISSUER_NAME = "requester.issuername";
    private static final String REQUESTER_SUBJECT = "javax.security.auth.Subject";
    private static final String KEYSTORE_TYPE = "JKS";
    private static final String DATE_FORMAT1 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String DATE_FORMAT2 = "yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'";
    private KeyStore keyStore;
    private KeyStore trustStore;
    private PrivateKey serverKey;
    private X509Certificate serverCert;
    private static final UnsupportedCallbackException unsupportedCallback =
            new UnsupportedCallbackException(null, "Unsupported Callback Type Encountered");

    public SecurityCallbackHandler() throws Exception {

        boolean securityEnabled = CommonProperties.getInstance().getBoolean("security.enabled");

        if (!securityEnabled) {
            return;
        }
        
        String keyStoreURL = CommonProperties.getInstance().get("security.keystore.path");
        String keyStorePassword = CommonProperties.getInstance().get("security.keystore.password");
        String serverCertAlias = CommonProperties.getInstance().get("security.keystore.cert.alias");
        String serverCertPw = CommonProperties.getInstance().get("security.keystore.cert.password");

        String trustStoreURL = CommonProperties.getInstance().get("security.truststore.path");
        String trustStorePassword = CommonProperties.getInstance().get("security.truststore.password");

        try {
            keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
            keyStore.load(new FileInputStream(keyStoreURL), keyStorePassword.toCharArray());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "could not load keystore from path: " + keyStoreURL, e);
            throw new IOException(e.getMessage());
        }

        serverKey = (PrivateKey) keyStore.getKey(serverCertAlias, serverCertPw.toCharArray());
        serverCert = (X509Certificate) keyStore.getCertificate(serverCertAlias);

        try {
            trustStore = KeyStore.getInstance(KEYSTORE_TYPE);
            trustStore.load(new FileInputStream(trustStoreURL), trustStorePassword.toCharArray());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "could not load keystore from path: " + trustStoreURL, e);
            throw new IOException(e.getMessage());
        }
    }

    /**
     * @param callbacks an array of <code>Callback</code> objects provided
     *                  by an underlying security service which contains
     *                  the information requested to be retrieved or displayed.
     * @throws java.io.IOException if an input or output error occurs.
     * @throws javax.security.auth.callback.UnsupportedCallbackException
     *                             if the implementation of this
     *                             method does not support one or more of the Callbacks
     *                             specified in the <code>callbacks</code> parameter.
     */
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

        for (Callback callback : callbacks) {

            if (logger.isLoggable(Level.FINE)) {
                logger.info("callback: " + callback.getClass().getName());
            }

            if (callback instanceof SignatureVerificationKeyCallback) {
                handleSignatureVerificationKey((SignatureVerificationKeyCallback) callback);
            } else if (callback instanceof SignatureKeyCallback) {
                handleSignatureKey((SignatureKeyCallback) callback);
            } else if (callback instanceof DecryptionKeyCallback) {
                handleDecryptionKey((DecryptionKeyCallback) callback);
            } else if (callback instanceof EncryptionKeyCallback) {
                handleEncryptionKey((EncryptionKeyCallback) callback);
            } else if (callback instanceof CertificateValidationCallback) {
                handleCertificateValidation((CertificateValidationCallback) callback);
            } else if (callback instanceof TimestampValidationCallback) {
                handleTimestampValidation((TimestampValidationCallback) callback);
            } else {
                throw unsupportedCallback;
            }
        }
    }

    /**
     * handles a signature key verification callback
     * @param callback Callback to handle
     * @throws IOException
     * @throws UnsupportedCallbackException
     */
    private void handleSignatureVerificationKey(SignatureVerificationKeyCallback cb) throws IOException, UnsupportedCallbackException {

        if (logger.isLoggable(Level.FINE)) {
            logger.fine("type " + cb.getRequest().getClass().getSimpleName());
        }

        if (cb.getRequest() instanceof SignatureVerificationKeyCallback.X509SubjectKeyIdentifierBasedRequest) {
            SignatureVerificationKeyCallback.X509SubjectKeyIdentifierBasedRequest request =
                    (SignatureVerificationKeyCallback.X509SubjectKeyIdentifierBasedRequest) cb.getRequest();
            X509Certificate cert = getCertificateFromTrustStore(request.getSubjectKeyIdentifier());
            request.setX509Certificate(cert);

            if (logger.isLoggable(Level.INFO)) {
                logger.info("sig verification key: " + cert.getSubjectDN().toString());
            }
        } else if (cb.getRequest() instanceof SignatureVerificationKeyCallback.X509IssuerSerialBasedRequest) {
            SignatureVerificationKeyCallback.X509IssuerSerialBasedRequest request =
                    (SignatureVerificationKeyCallback.X509IssuerSerialBasedRequest) cb.getRequest();
            X509Certificate cert = getCertificateFromTrustStore(request.getIssuerName(), request.getSerialNumber());
            request.setX509Certificate(cert);

            if (logger.isLoggable(Level.INFO)) {
                logger.info("sig verification key: " + cert.getSubjectDN().toString());
            }
        } else {
            logger.warning("unsupported callback");
            throw unsupportedCallback;
        }
    }

    /**
     * handles a signature key callback
     * @param callback Callback to handle
     * @throws IOException
     * @throws UnsupportedCallbackException
     */
    private void handleSignatureKey(SignatureKeyCallback callback) throws IOException, UnsupportedCallbackException {

        if (callback.getRequest() instanceof SignatureKeyCallback.DefaultPrivKeyCertRequest) {
            SignatureKeyCallback.DefaultPrivKeyCertRequest request =
                    (SignatureKeyCallback.DefaultPrivKeyCertRequest) callback.getRequest();

            request.setPrivateKey(serverKey);
            request.setX509Certificate(serverCert);
        } else if (callback.getRequest() instanceof SignatureKeyCallback.AliasPrivKeyCertRequest) {
            SignatureKeyCallback.AliasPrivKeyCertRequest request =
                    (SignatureKeyCallback.AliasPrivKeyCertRequest) callback.getRequest();

            request.setPrivateKey(serverKey);
            request.setX509Certificate(serverCert);
        } else {
            throw unsupportedCallback;
        }
    }

    /**
     * handles an encryption key callback
     * @param callback Callback to handle
     * @throws IOException
     * @throws UnsupportedCallbackException
     */
    private void handleEncryptionKey(EncryptionKeyCallback callback) throws IOException, UnsupportedCallbackException {

        if (callback.getRequest() instanceof EncryptionKeyCallback.AliasX509CertificateRequest) {
            EncryptionKeyCallback.AliasX509CertificateRequest request =
                    (EncryptionKeyCallback.AliasX509CertificateRequest) callback.getRequest();
            
            String alias = request.getAlias();
            
            if ((alias == null) || ("".equals(alias))) {
                
                X509Certificate clientCert = null;
                Subject subject = (Subject) callback.getRuntimeProperties().get(REQUESTER_SUBJECT);

                if (subject != null) {
                    if (logger.isLoggable(Level.INFO)) {
                        logger.info("found subject");
                    }
                    Set publicCredentials = subject.getPublicCredentials();
                    for (Object credentials : publicCredentials) {
                        if (logger.isLoggable(Level.INFO)) {
                            logger.info("Subject public cred: " + credentials.getClass().getSimpleName());
                        }
                        if (credentials instanceof X509Certificate) {
                            clientCert = (X509Certificate) credentials;
//                            request.setX509Certificate(cert);
                        }
                    }
                }

                if (clientCert == null) {
                    if (logger.isLoggable(Level.INFO)) {
                        logger.info("try getting client cert by issuerName and serial");
                    }
                    
                    String issuerName = (String) callback.getRuntimeProperties().get(REQUESTER_ISSUER_NAME);
                    BigInteger serial = (BigInteger) callback.getRuntimeProperties().get(REQUESTER_SERIAL);


                    if (issuerName == null) {
                        logger.warning("issuerName not found in request");
                        return;
                    }
                    if (serial == null) {
                        logger.warning("serial not found in request");
                        return;
                    }

                    if (logger.isLoggable(Level.FINE)) {
                        logger.fine("client cert info:\n issuer: " + issuerName + "\n serial: " + serial);
                    }

                    clientCert = getCertificateFromTrustStore(issuerName, serial);

                    if (clientCert == null) {
                        logger.warning("client certificate not found\n issuer: " + issuerName + "\n serial: " + serial);
                    }
                }
                
                request.setX509Certificate(clientCert);
            } else {
                try {
                    X509Certificate cert = (X509Certificate) trustStore.getCertificate(alias);
                    request.setX509Certificate(cert);
                } catch (Exception e) {
                    throw new IOException(e.getMessage());
                }
            }
        } else {
            throw unsupportedCallback;
        }
    }

    /**
     * handles a decryption key callback
     * @param callback Callback to handle
     * @throws IOException
     * @throws UnsupportedCallbackException
     */
    private void handleDecryptionKey(DecryptionKeyCallback callback) throws IOException, UnsupportedCallbackException {

        if (callback.getRequest() instanceof DecryptionKeyCallback.X509SubjectKeyIdentifierBasedRequest) {
            ((DecryptionKeyCallback.X509SubjectKeyIdentifierBasedRequest) callback.getRequest()).setPrivateKey(serverKey);

        } else if (callback.getRequest() instanceof DecryptionKeyCallback.X509IssuerSerialBasedRequest) {
            ((DecryptionKeyCallback.X509IssuerSerialBasedRequest) callback.getRequest()).setPrivateKey(serverKey);

        } else if (callback.getRequest() instanceof DecryptionKeyCallback.X509CertificateBasedRequest) {
            ((DecryptionKeyCallback.X509CertificateBasedRequest) callback.getRequest()).setPrivateKey(serverKey);

        } else {
            throw unsupportedCallback;
        }
    }

    /**
     * handles a certificate validation callback
     * @param callback Callback to handle
     */
    private void handleCertificateValidation(CertificateValidationCallback callback) {
        callback.setValidator(new X509CertificateValidatorImpl());
    }

    /**
     * handles a timestamp validation callback
     * @param callback Callback to handle
     */
    private void handleTimestampValidation(TimestampValidationCallback callback) {
        callback.setValidator(new DefaultTimestampValidator());
    }

    /**
     * Returns the Subject Key Identifier of a certificate
     * @param cert
     * @return byte[] of Subject Key Identifier
     */
    private static byte[] getSubjectKeyIdentifier(X509Certificate cert) {
        String SUBJECT_KEY_IDENTIFIER_OID = "2.5.29.14";
        byte[] subjectKeyIdentifier = cert.getExtensionValue(SUBJECT_KEY_IDENTIFIER_OID);
        if (subjectKeyIdentifier == null) {
            return null;
        }
        try {
            sun.security.x509.KeyIdentifier keyId = null;
            sun.security.util.DerValue derVal = new sun.security.util.DerValue(
                    new sun.security.util.DerInputStream(subjectKeyIdentifier).getOctetString());
            keyId = new sun.security.x509.KeyIdentifier(derVal.getOctetString());
            return keyId.getIdentifier();
        } catch (NoClassDefFoundError ncde) {
            if (subjectKeyIdentifier == null) {
                return null;
            }
            byte[] dest = new byte[subjectKeyIdentifier.length - 4];
            System.arraycopy(subjectKeyIdentifier, 4, dest, 0, subjectKeyIdentifier.length - 4);
            return dest;
        } catch (java.io.IOException ex) {
            //ignore
            return null;
        }
    }

    /**
     * Gets a certificate from truststore
     * @param ski
     * @return
     * @throws IOException
     */
    private X509Certificate getCertificateFromTrustStore(byte[] ski) throws IOException {
        try {
            Enumeration aliases = trustStore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = (String) aliases.nextElement();
                Certificate cert = trustStore.getCertificate(alias);
                if (cert == null || !"X.509".equals(cert.getType())) {
                    continue;
                }
                X509Certificate x509Cert = (X509Certificate) cert;
                byte[] keyId = getSubjectKeyIdentifier(x509Cert);
                if (keyId == null) {
                    // Cert does not contain a key identifier
                    continue;
                }
                if (Arrays.equals(ski, keyId)) {
                    return x509Cert;
                }
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
        return null;
    }

    /**
     * Gets a certificate from truststore
     * @param issuerName
     * @param serialNumber
     * @return
     * @throws IOException
     */
    private X509Certificate getCertificateFromTrustStore(String issuerName, BigInteger serialNumber) throws IOException {
        try {
            Enumeration aliases = trustStore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = (String) aliases.nextElement();
                Certificate cert = trustStore.getCertificate(alias);
                if (cert == null || !"X.509".equals(cert.getType())) {
                    continue;
                }
                X509Certificate x509Cert = (X509Certificate) cert;
                String thisIssuerName = RFC2253Parser.normalize(x509Cert.getIssuerDN().getName());
                BigInteger thisSerialNumber = x509Cert.getSerialNumber();
                if (thisIssuerName.equals(issuerName) && thisSerialNumber.equals(serialNumber)) {
                    return x509Cert;
                }
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
        return null;
    }

    /**
     * Util class to validate X509Certificates
     */
    private class X509CertificateValidatorImpl implements CertificateValidationCallback.CertificateValidator {

        /**
         * Checks if certificates date is valid
         * @param certificate
         * @return boolean
         * @throws CertificateValidationCallback.CertificateValidationException
         */
        public boolean validate(X509Certificate certificate) throws CertificateValidationCallback.CertificateValidationException {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("validating cert: " + certificate.getSubjectDN().toString());
            }

            if (isSelfCert(certificate)) {
                return true;
            }
            try {
                certificate.checkValidity();
            } catch (CertificateExpiredException e) {
                e.printStackTrace();
                throw new CertificateValidationCallback.CertificateValidationException("X509Certificate Expired", e);
            } catch (CertificateNotYetValidException e) {
                e.printStackTrace();
                throw new CertificateValidationCallback.CertificateValidationException("X509Certificate not yet valid", e);
            }
            X509CertSelector certSelector = new X509CertSelector();
            certSelector.setCertificate(certificate);
            PKIXBuilderParameters parameters;
            CertPathBuilder builder;
            try {
                parameters = new PKIXBuilderParameters(trustStore, certSelector);
                parameters.setRevocationEnabled(false);
                builder = CertPathBuilder.getInstance("PKIX");
            } catch (Exception e) {
                e.printStackTrace();
                throw new CertificateValidationCallback.CertificateValidationException(e.getMessage(), e);
            }
            try {
                PKIXCertPathBuilderResult result = (PKIXCertPathBuilderResult) builder.build(parameters);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        /**
         * Checks if certificate is self signed.
         * @param cert
         * @return boolean
         * @throws CertificateValidationCallback.CertificateValidationException
         */
        private boolean isSelfCert(X509Certificate cert) throws CertificateValidationCallback.CertificateValidationException {
            try {
                Enumeration aliases = keyStore.aliases();
                while (aliases.hasMoreElements()) {
                    String alias = (String) aliases.nextElement();
                    if (keyStore.isKeyEntry(alias)) {
                        X509Certificate x509Cert =
                                (X509Certificate) keyStore.getCertificate(alias);
                        if (x509Cert != null) {
                            if (x509Cert.equals(cert)) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                throw new CertificateValidationCallback.CertificateValidationException(e.getMessage(), e);
            }
        }
    }

    /**
     * timestamp validation
     */
    private class DefaultTimestampValidator implements TimestampValidationCallback.TimestampValidator {

        public void validate(TimestampValidationCallback.Request request) throws TimestampValidationCallback.TimestampValidationException {

            if (request instanceof TimestampValidationCallback.UTCTimestampRequest) {
                TimestampValidationCallback.UTCTimestampRequest utcRequest = (TimestampValidationCallback.UTCTimestampRequest) request;
                Date created = parseDate(utcRequest.getCreated());

                validateCreationTime(created, utcRequest.getMaxClockSkew(), utcRequest.getTimestampFreshnessLimit());

                if (utcRequest.getExpired() != null) {
                    Date expired = parseDate(utcRequest.getExpired());
                    validateExpirationTime(expired, utcRequest.getMaxClockSkew());
                }
            } else {
                throw new TimestampValidationCallback.TimestampValidationException("Unsupport request: [" + request + "]");
            }
        }

        private Date getFreshnessAndSkewAdjustedDate(long maxClockSkew, long timestampFreshnessLimit) {
            Calendar c = new GregorianCalendar();
            long offset = c.get(Calendar.ZONE_OFFSET);
            if (c.getTimeZone().inDaylightTime(c.getTime())) {
                offset += c.getTimeZone().getDSTSavings();
            }
            long beforeTime = c.getTimeInMillis();
            long currentTime = beforeTime - offset;

            long adjustedTime = currentTime - maxClockSkew - timestampFreshnessLimit;
            c.setTimeInMillis(adjustedTime);

            return c.getTime();
        }

        private Date getGMTDateWithSkewAdjusted(Calendar calendar, long maxClockSkew, boolean addSkew) {
            long offset = calendar.get(Calendar.ZONE_OFFSET);
            if (calendar.getTimeZone().inDaylightTime(calendar.getTime())) {
                offset += calendar.getTimeZone().getDSTSavings();
            }
            long beforeTime = calendar.getTimeInMillis();
            long currentTime = beforeTime - offset;

            if (addSkew) {
                currentTime = currentTime + maxClockSkew;
            } else {
                currentTime = currentTime - maxClockSkew;
            }

            calendar.setTimeInMillis(currentTime);
            return calendar.getTime();
        }

        private Date parseDate(String date) throws TimestampValidationCallback.TimestampValidationException {
            SimpleDateFormat calendarFormatter1 = new SimpleDateFormat(DATE_FORMAT1);
            SimpleDateFormat calendarFormatter2 = new SimpleDateFormat(DATE_FORMAT2);

            try {
                try {
                    return calendarFormatter1.parse(date);
                } catch (ParseException ignored) {
                    return calendarFormatter2.parse(date);
                }
            } catch (ParseException ex) {
                throw new TimestampValidationCallback.TimestampValidationException("Could not parse request date: " + date, ex);
            }
        }

        private void validateCreationTime(Date created, long maxClockSkew, long timestampFreshnessLimit)
                throws TimestampValidationCallback.TimestampValidationException {
            Date current = getFreshnessAndSkewAdjustedDate(maxClockSkew, timestampFreshnessLimit);

            if (created.before(current)) {
                throw new TimestampValidationCallback.TimestampValidationException(
                        "The creation time is older than  currenttime - timestamp-freshness-limit - max-clock-skew");
            }

            Date currentTime = getGMTDateWithSkewAdjusted(new GregorianCalendar(), maxClockSkew, true);
            if (currentTime.before(created)) {
                throw new TimestampValidationCallback.TimestampValidationException(
                        "The creation time is ahead of the current time.");
            }
        }

        private void validateExpirationTime(Date expires, long maxClockSkew)
                throws TimestampValidationCallback.TimestampValidationException {

            Date currentTime = getGMTDateWithSkewAdjusted(new GregorianCalendar(), maxClockSkew, false);

            if (expires.before(currentTime)) {
                throw new TimestampValidationCallback.TimestampValidationException(
                        "The current time is ahead of the expiration time in Timestamp");
            }
        }
    }
}
