/**
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 *
 */

package se.vgregion.portal.repository;

import se.vgregion.portal.iframe.model.UserSiteCredential;

/**
 * This action do that and that, if it has something special it is.
 *
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public interface CredentialStoreRepository {

    /**
     * Retrive credental.
     *
     * @param uid - user identifier
     * @param siteKey - site identifier
     * @return credentail
     */
    UserSiteCredential getUserSiteCredential(String uid, String siteKey);

    /**
     * Store credental.
     *
     * @param siteCredential - siteCredential
     */
    void addUserSiteCredential(UserSiteCredential siteCredential);
}
