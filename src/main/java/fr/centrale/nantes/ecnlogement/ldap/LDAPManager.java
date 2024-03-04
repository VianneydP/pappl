/* -----------------------------------------
 * Projet Kepler
 *
 * Ecole Centrale Nantes
 * Jean-Yves MARTIN
 * ----------------------------------------- */
package fr.centrale.nantes.ecnlogement.ldap;

import java.io.UncheckedIOException;
import java.net.DatagramSocket;
import java.util.ResourceBundle;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author kwyhr
 */
public class LDAPManager {

    private String ldapBasedn;
    private String ldapHost;
    private String ldapSecurityProtocol;
    private String loginField;
    private boolean ldapAvailable;

    private static LDAPManager ldapServer;
    private static boolean initialized = false;

    /**
     *
     */
    public LDAPManager() {
        init();
        if (ldapServer != null) {
            this.ldapHost = ldapServer.ldapHost;
            this.ldapBasedn = ldapServer.ldapBasedn;
            this.ldapSecurityProtocol = ldapServer.ldapSecurityProtocol;
            this.loginField = ldapServer.loginField;

            this.ldapAvailable = ldapServer.ldapAvailable;
        }
    }

    /**
     * Initialize LDAP informations
     */
    private static void init() {
        if (!initialized) {
            initialized = true;
            ldapServer = null;
            ldapServer = new LDAPManager();
            try {
                ldapServer.ldapAvailable = false;
                // USE config parameters
                ResourceBundle res = ResourceBundle.getBundle(LDAPManager.class.getPackage().getName() + ".ldap");
                ldapServer.ldapHost = res.getString("ldapHost");
                ldapServer.ldapBasedn = res.getString("ldapBasedn");
                ldapServer.ldapSecurityProtocol = res.getString("ldapSecurityProtocol");
                ldapServer.loginField = res.getString("loginField");

                if ((ldapServer.ldapHost != null) && (ldapServer.ldapBasedn != null) && (ldapServer.ldapSecurityProtocol != null)
                        && (!ldapServer.ldapHost.isEmpty()) && (!ldapServer.ldapBasedn.isEmpty())) {
                    ldapServer.checkConnexion();
                }
            } catch (Exception ex) {
                Logger.getLogger(LDAPManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Check if LDAP is available
     * @return 
     */
    public boolean isAvailable() {
        return ldapAvailable;
    }

    /**
     * Check if datagram can connect to the server on the specific port
     *
     * @param socket
     * @param server
     * @param port
     */
    private void checkDatagram(DatagramSocket socket, InetAddress server, int port) {
        try {
            ldapAvailable = false;
            socket.connect(server, port);
            ldapAvailable = true;
            socket.disconnect();
        } catch (IllegalArgumentException | SecurityException | UncheckedIOException ex) {
        }
    }

    /**
     * Check if we can connect to the LDAP server
     */
    private void checkConnexion() {
        int pos1 = ldapHost.indexOf("://");
        int pos2 = ldapHost.indexOf(":", pos1 + 1);
        String serverName = ldapHost.substring(pos1 + 3, pos2);
        int connexionPort = Integer.parseInt(ldapHost.substring(pos2 + 1));

        try {
            InetAddress server = InetAddress.getByName(serverName);
            DatagramSocket socket = new DatagramSocket();
            checkDatagram(socket, server, connexionPort);
            socket.close();
        } catch (UnknownHostException | SocketException ex) {
        }
    }

    /**
     * Build LDAP properties for a BIND
     *
     * @param login
     * @param password
     * @return
     */
    private Properties getLDAPProperties(String login, String password) {
        Properties env = new Properties();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapHost);
        if (ldapSecurityProtocol.equals("ssl")) {
            // Add SSL encription
            env.put(Context.SECURITY_PROTOCOL, "ssl");
            // Use locally defined socked manager to avoid certificate validation
            env.put("java.naming.ldap.factory.socket", "fr.centrale.nantes.kepler.ldap.MySSLSocketFactory");
        }
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, loginField + "=" + login + "," + ldapBasedn);
        env.put(Context.SECURITY_CREDENTIALS, password);

        return env;
    }

    /**
     * Identify with a LDAP BIND
     *
     * @param login
     * @param password
     * @return
     */
    public boolean authenticate(String login, String password) {
        boolean isAuthenticated = false;
        if ((ldapAvailable) && (login != null) && (password != null) && (!login.isEmpty()) && (!password.isEmpty())) {
            try {
                Properties env = getLDAPProperties(login, password);
                DirContext ctx = new InitialDirContext(env);
                isAuthenticated = true;
                ctx.close();
            } catch (AuthenticationException ex) {
                // Not recognized
            } catch (CommunicationException ex) {
                if (ldapAvailable) {
                    Logger.getLogger(LDAPManager.class.getName()).log(Level.SEVERE, null, ex);
                } else {
                    ldapAvailable = false;
                }
            } catch (NamingException ex) {
                // Not recognized
            }
        }
        return isAuthenticated;
    }

}
