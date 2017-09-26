/*
 * Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package java.awt.desktop;

/**
 * Event sent when the user session has been changed.
 *
 * Some systems may provide a reason of a user session change.
 *
 * @see UserSessionListener#userSessionActivated(UserSessionEvent)
 * @see UserSessionListener#userSessionDeactivated(UserSessionEvent)
 *
 * @since 9
 */
public final class UserSessionEvent extends AppEvent {

    private static final long serialVersionUID = 6747138462796569055L;

    private final Reason reason;

    /**
     * Kinds of available reasons of user session change.
     */
    public static enum Reason {
        /**
         * The system does not provide a reason for a session change.
         */
        UNSPECIFIED,

        /**
         * The session was connected/disconnected to the console terminal.
         */
        CONSOLE,

        /**
         * The session was connected/disconnected to the remote terminal.
         */
        REMOTE,

        /**
         * The session has been locked/unlocked.
         */
        LOCK
    }

    /**
     * Constructs a {@code UserSessionEvent}
     *
     * @param reason of session change
     */
    public UserSessionEvent(Reason reason) {
        this.reason = reason;
    }

    /**
     * Gets a reason of the user session change.
     *
     * @return reason a reason
     * @see Reason#UNSPECIFIED
     * @see Reason#CONSOLE
     * @see Reason#REMOTE
     * @see Reason#LOCK
     */
    public Reason getReason() {
        return reason;
    }
}
