/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
 
package fr.centrale.nantes.ecnlogement.ldap;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/**
 * Dummy certificate manager
 */
public class DummyTrustManager implements X509TrustManager {

    /**
     * Check client
     * Do not return exception to validate a certificate
     * @param xcs
     * @param string
     * @throws CertificateException
     */
    @Override
    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        // do nothing
    }

    /**
     * Check server
     * Do not return exception to validate certificate
     * @param xcs
     * @param string
     * @throws CertificateException
     */
    @Override
    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        // do nothing
    }

    /**
     * Accept issuers
     * @return
     */
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new java.security.cert.X509Certificate[0];
    }

}
