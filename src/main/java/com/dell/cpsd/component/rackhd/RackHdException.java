/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.component.rackhd;

import com.dell.cpsd.component.common.exception.ComponentServicesException;

/**
 * This is the Rack HD Exception class.
 *
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 * </p>
 * 
 * @version 1.0
 * 
 * @since 1.0
 */
public class RackHdException extends ComponentServicesException
{
    /**
     * RackHdException constructor.
     *
     * @param message
     *            The exception message.
     * @since 1.0
     */
    public RackHdException(String message)
    {
        super(message);
    }

    /**
     * RackHdException constructor.
     *
     * @param message
     *            The exception message.
     * @param cause
     *            The cause of the exception.
     * @since 1.0
     */
    public RackHdException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * RackHdException constructor.
     *
     * @param cause
     *            The cause of the exception.
     * @since 1.0
     */
    public RackHdException(Throwable cause)
    {
        super(cause);
    }

    /**
     * RackHdException constructor.
     *
     * @param message
     *            The exception message.
     * @param cause
     *            The cause of the exception.
     * @param writableStackTrace
     *           whether or not the stack track should be writable
     * @param enableSuppression
     *            whether or not the exception should be suppressed
     * @since 1.0
     */
    public RackHdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
