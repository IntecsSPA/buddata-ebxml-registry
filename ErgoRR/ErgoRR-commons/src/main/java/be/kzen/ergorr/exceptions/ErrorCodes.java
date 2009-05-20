/*
 * Project: Buddata ebXML RegRep
 * Class: ErrorCodes.java
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
package be.kzen.ergorr.exceptions;

/**
 *
 * @author yamanustuntas
 */
public interface ErrorCodes {
    public static final String NOT_SUPPORTED = "NotSupported";
    public static final String TRANSACTION_FAILED = "TransactionFailed";
    public static final String INVALID_REQUEST = "InvalidRequest";
    public static final String NOT_IMPLEMENTED = "NotImplemented";
    public static final String NOT_FOUND = "NotFound";
    public static final String INTERNAL_ERROR = "InternalError";
}
