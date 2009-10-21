/*
 * Project: Buddata ebXML RegRep
 * Class: RegistryPackageTypeV.java
 * Copyright (C) 2008 Yaman Ustuntas
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
package be.kzen.ergorr.service.validator;

import be.kzen.ergorr.exceptions.InvalidReferenceException;
import be.kzen.ergorr.exceptions.ReferenceExistsException;
import be.kzen.ergorr.model.rim.IdentifiableType;
import be.kzen.ergorr.model.rim.RegistryPackageType;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;

/**
 * Validate RegistryPackages.
 * 
 * @author yamanustuntas
 */
public class RegistryPackageTypeV extends RegistryObjectTypeV<RegistryPackageType> {

    private static Logger logger = Logger.getLogger(RegistryPackageTypeV.class.getName());

    @Override
    public void validate() throws InvalidReferenceException, SQLException {
        super.validate();
    }

    @Override
    public void validateToDelete() throws ReferenceExistsException, SQLException {
        super.validateToDelete();

        String sql = "select * from t_identifiable t_i inner join t_association t_a on t_i.id = t_a.targetobject where " +
                "t_a.sourceobject = '" + rimObject.getId() + "'" +
                "and t_a.associationtype = 'urn:oasis:names:tc:ebxml-regrep:AssociationType:HasMember'";

        List<JAXBElement<? extends IdentifiableType>> identEls = persistence.query(sql, null, (Class) IdentifiableType.class);

        for (JAXBElement<? extends IdentifiableType> identEl : identEls) {
            IdentifiableType ident = identEl.getValue();

            if (!idExistsInRequest(ident.getId())) {
                String validatorClassName = "be.kzen.ergorr.service.validator." + ident.getClass().getSimpleName() + "V";

                try {
                    Class validatorClass = Class.forName(validatorClassName);
                    RegistryObjectTypeV validator = (RegistryObjectTypeV) validatorClass.newInstance();
                    validator.setFlatIdents(flatIdents);
                    validator.setRimObject(ident);
                    validator.setRequestContext(requestContext);
                    validator.validateToDelete();
                    addedIdents.add(ident);
                } catch (InstantiationException ex) {
                    logger.log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    logger.log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
