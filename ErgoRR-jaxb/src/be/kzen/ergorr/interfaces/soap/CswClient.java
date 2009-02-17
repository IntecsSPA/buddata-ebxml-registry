/*
 * Project: Buddata ebXML RegRep
 * Class: CswClient.java
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
package be.kzen.ergorr.interfaces.soap;

import be.kzen.ergorr.model.csw.CapabilitiesType;
import be.kzen.ergorr.model.csw.DescribeRecordResponseType;
import be.kzen.ergorr.model.csw.DescribeRecordType;
import be.kzen.ergorr.model.csw.GetCapabilitiesType;
import be.kzen.ergorr.model.csw.GetDomainResponseType;
import be.kzen.ergorr.model.csw.GetDomainType;
import be.kzen.ergorr.model.csw.GetRecordByIdResponseType;
import be.kzen.ergorr.model.csw.GetRecordByIdType;
import be.kzen.ergorr.model.csw.GetRecordsResponseType;
import be.kzen.ergorr.model.csw.GetRecordsType;
import be.kzen.ergorr.model.csw.HarvestResponseType;
import be.kzen.ergorr.model.csw.HarvestType;
import be.kzen.ergorr.model.csw.TransactionResponseType;
import be.kzen.ergorr.model.csw.TransactionType;

/**
 *
 * @author yamanustuntas
 */
public interface CswClient {

        /**
     * Invokes the CSW GetRecords service operation.
     *
     * @param request
     * @return
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public GetRecordsResponseType getRecords(GetRecordsType request) throws ServiceExceptionReport;

    /**
     * Invokes the CSW GetRecordById service operation.
     *
     * @param request
     * @return
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public GetRecordByIdResponseType getRecordById(GetRecordByIdType request) throws ServiceExceptionReport;

    /**
     * Invokes the CSW Transaction service operation.
     *
     * @param request
     * @return
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public TransactionResponseType transact(TransactionType request) throws ServiceExceptionReport;

    /**
     * Invokes the CSW Harvest service operation.
     *
     * @param request
     * @return
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public HarvestResponseType harvest(HarvestType request) throws ServiceExceptionReport;

    /**
     * Invokes the CSW RecordDescription service operation.
     *
     * @param request
     * @return
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public DescribeRecordResponseType getRecordDescription(DescribeRecordType request) throws ServiceExceptionReport;

    /**
     * Invokes the CSW Domain service operation.
     *
     * @param request
     * @return
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public GetDomainResponseType getDomain(GetDomainType request) throws ServiceExceptionReport;

    /**
     * Invokes the CSW Capabilities service operation.
     *
     * @param request
     * @return
     * @throws be.kzen.ergorr.interfaces.soap.ServiceExceptionReport
     */
    public CapabilitiesType getCapabilities(GetCapabilitiesType request) throws ServiceExceptionReport;
}
