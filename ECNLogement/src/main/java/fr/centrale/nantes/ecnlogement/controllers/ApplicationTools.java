/* -----------------------------------------
 * Projet ECN Logement
 *
 * Ecole Centrale Nantes
 * Vianney de Ponthaud - Maxence Nicolet
 * ----------------------------------------- */
package fr.centrale.nantes.ecnlogement.controllers;

import fr.centrale.nantes.ecnlogement.repositories.ConnexionRepository;
import fr.centrale.nantes.ecnlogement.items.Connexion;

import fr.centrale.nantes.ecnlogement.repositories.PersonneRepository;
import fr.centrale.nantes.ecnlogement.items.Personne;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author kwyhr
 */
public class ApplicationTools {

    private static final String[] HEADERS_TO_TRY = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR"};
    private static BCryptPasswordEncoder bCryptPasswordEncoder = null;
    private static final String MULTIPART = "MULTIPART";
    private static final String MULTIPARTFILES = "MULTIPARTFILES";

    /**
     * Check if a String is a Time and returns the Date, null otherwise
     *
     * @param aTime
     * @return
     */
    private static Date checkDateFormats(String aTime, LinkedList<String> possibleFormats) {
        Date returnedValue = null;
        if (aTime != null) {
            if (!aTime.equals("")) {
                String format = null;
                while ((returnedValue == null) && (!possibleFormats.isEmpty())) {
                    format = possibleFormats.removeFirst();

                    try {
                        // Hour, Minustes and seconds
                        SimpleDateFormat aFormater = new SimpleDateFormat(format);
                        returnedValue = aFormater.parse(aTime);
                    } catch (ParseException ex) {
                    }
                }

                if (returnedValue != null) {
                    Calendar aCalendar = Calendar.getInstance();
                    aCalendar.setTime(returnedValue);
                    possibleFormats.addFirst(format);
                }
            }
        }

        return returnedValue;
    }

    /**
     * Check if a String is a timestamp and returns the Date, null otherwise
     *
     * @param aDate
     * @return
     */
    public static Date isTimestamp(String aDate) {
        LinkedList<String> possibleFormats = new LinkedList<String>();
        possibleFormats.add("dd/MM/yyyy");
        possibleFormats.add("yyyy-MM-dd");
        possibleFormats.add("yyyy-dd-MM");

        checkDateFormats(aDate, possibleFormats);
        String format = possibleFormats.removeFirst();

        possibleFormats.clear();
        possibleFormats.add(format + " HH:mm:ss");
        possibleFormats.add(format + " HH:mm");
        possibleFormats.add(format + " HH");

        Date returnedValue = checkDateFormats(aDate, possibleFormats);
        return returnedValue;
    }

    /**
     * Check if a String is a Date and returns the Date, null otherwise
     *
     * @param aDate
     * @return
     */
    public static Date isDate(String aDate) {
        LinkedList<String> possibleFormats = new LinkedList<String>();
        possibleFormats.add("dd/MM/yyyy");
        possibleFormats.add("yyyy-MM-dd");
        possibleFormats.add("yyyy-dd-MM");

        Date returnedValue = checkDateFormats(aDate, possibleFormats);
        return returnedValue;
    }

    /**
     * Get current Date
     *
     * @return
     */
    public static Date getCurrentDate() {
        Calendar aCalendar = Calendar.getInstance();
        Date current = aCalendar.getTime();
        return current;
    }

    /**
     * Get int from String
     *
     * @param value
     * @return
     */
    public static int getIntFromString(String value) {
        int intValue = -1;
        if ((value != null) && (!value.isEmpty())) {
            try {
                intValue = Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                Logger.getLogger(ApplicationTools.class.getName()).log(Level.WARNING, null, ex);
            }
        }
        return intValue;
    }

    /**
     * Get BigInteger from String
     *
     * @param value
     * @return
     */
    public static BigInteger getBigIntegerFromString(String value) {
        BigInteger intValue = new BigInteger(value);
        return intValue;
    }

    /**
     * Get float from String
     *
     * @param value
     * @return
     */
    public static float getFloatFromString(String value) {
        float floatValue = 0.0f;
        if ((value != null) && (!value.isEmpty())) {
            try {
                floatValue = Float.parseFloat(value);
            } catch (NumberFormatException ex) {
                Logger.getLogger(ApplicationTools.class.getName()).log(Level.WARNING, null, ex);
            }
        }
        return floatValue;
    }

    private static void parseRequest(HttpServletRequest request) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            // MULTIPART request
            if (request.getAttribute("MULTIPART") == null) {
                // Not parsed
                HashMap<String, String> temp = new HashMap<>();
                request.setAttribute(MULTIPART, temp);
                HashMap<String, File> tempFiles = new HashMap<>();
                request.setAttribute(MULTIPARTFILES, tempFiles);

                try {
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
                    factory.setSizeThreshold(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD);
                    factory.setFileCleaningTracker(null);

                    ServletFileUpload upload = new ServletFileUpload(factory);
                    FileItemIterator iterator = upload.getItemIterator(request);
                    while (iterator.hasNext()) {
                        FileItemStream item = iterator.next();
                        InputStream stream = item.openStream();
                        String itemName = item.getFieldName();
                        if (item.isFormField()) {
                            // Form field
                            String itemValue = Streams.asString(stream);
                            temp.put(itemName, itemValue);
                        } else {
                            // File
                            Path tempFile = Files.createTempFile("kepler", ".tmp");
                            InputStream is = item.openStream();
                            File osFile = tempFile.toFile();
                            OutputStream os = new FileOutputStream(osFile);
                            try {
                                byte[] buffer = new byte[1024];
                                int length;
                                while ((length = is.read(buffer)) > 0) {
                                    os.write(buffer, 0, length);
                                }
                            } finally {
                                is.close();
                                os.close();
                            }
                            temp.put(itemName, osFile.getAbsolutePath());
                            tempFiles.put(itemName, osFile);
                        }
                    }

                } catch (FileUploadException ex) {
                    Logger.getLogger(ApplicationTools.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ApplicationTools.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * check Request parameter exists
     *
     * @param request
     * @param value
     * @return
     */
    public static boolean hasRequestParameter(HttpServletRequest request, String value) {
        parseRequest(request);

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            HashMap<String, String> temp = (HashMap<String, String>) (request.getAttribute(MULTIPART));
            return temp.containsKey(value);
        } else {
            // Standart request
            return (request.getParameter(value) != null);
        }
    }

    /**
     * Get String from Request
     *
     * @param request
     * @param value
     * @return
     */
    private static String genericStringFromRequest(HttpServletRequest request, String value) {
        parseRequest(request);

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            HashMap<String, String> temp = (HashMap<String, String>) (request.getAttribute(MULTIPART));
            return temp.get(value);
        } else {
            // Standart request
            return request.getParameter(value);
        }
    }

    /**
     * Clean request created temp files
     *
     * @param request
     */
    public static void cleanRequest(HttpServletRequest request) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            HashMap<String, File> temp = (HashMap<String, File>) (request.getAttribute(MULTIPARTFILES));
            if (temp != null) {
                for (String fileName : temp.keySet()) {
                    File tempFile = temp.get(fileName);
                    tempFile.delete();
                }
            }
        }
    }

    /**
     * Get String from request
     *
     * @param request
     * @param value
     * @return
     */
    public static String getStringFromRequest(HttpServletRequest request, String value) {
        return genericStringFromRequest(request, value);
    }

    /**
     * Get String from request
     *
     * @param request
     * @param value
     * @param maxLength
     * @return
     */
    public static String getStringFromRequest(HttpServletRequest request, String value, int maxLength) {
        String res = genericStringFromRequest(request, value);
        return res.substring(0, maxLength);
    }

    /**
     * Get String from request
     *
     * @param request
     * @param value
     * @return
     */
    public static File getFileFromRequest(HttpServletRequest request, String value) {
        parseRequest(request);

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            HashMap<String, File> temp = (HashMap<String, File>) (request.getAttribute(MULTIPARTFILES));
            return temp.get(value);
        }
        return null;
    }

    /**
     * Get int from request
     *
     * @param request
     * @param value
     * @return
     */
    public static int getIntFromRequest(HttpServletRequest request, String value) {
        return getIntFromString(genericStringFromRequest(request, value));
    }

    /**
     * Get BigInteger from request
     *
     * @param request
     * @param value
     * @return
     */
    public static BigInteger getBigIntegerFromRequest(HttpServletRequest request, String value) {
        return getBigIntegerFromString(genericStringFromRequest(request, value));
    }

    /**
     * Get float from request
     *
     * @param request
     * @param value
     * @return
     */
    public static float getFloatFromRequest(HttpServletRequest request, String value) {
        return getFloatFromString(genericStringFromRequest(request, value));
    }

    /**
     * Get boolean from request
     *
     * @param request
     * @param value
     * @return
     */
    public static boolean getBooleanFromRequest(HttpServletRequest request, String value) {
        String temp = genericStringFromRequest(request, value);
        if ((temp == null) || (temp.isEmpty())) {
            return false;
        } else {
            temp = temp.toLowerCase();
            if (temp.equals("true")) {
                return true;
            } else if (temp.equals("false")) {
                return false;
            } else {
                return (getIntFromString(temp) != 0);
            }
        }
    }

    /**
     * Get Date from request
     *
     * @param request
     * @param value
     * @return
     */
    public static Date getDateFromRequest(HttpServletRequest request, String value) {
        String temp = genericStringFromRequest(request, value);
        if ((temp == null) || (temp.isEmpty())) {
            return null;
        } else {
            return isDate(temp);
        }
    }

    /**
     * Get Array from request. Array id indexed with integers.
     *
     * @param request
     * @param value
     * @return
     */
    public static List<String> getIndexedArrayFromRequest(HttpServletRequest request, String value) {
        List<String> returnedValue = new ArrayList<String>();
        String searchFor = value + "[";
        int len = searchFor.length();

        parseRequest(request);
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        // Build param map
        HashMap<String, String> temp;
        if (isMultipart) {
            temp = (HashMap<String, String>) (request.getAttribute(MULTIPART));
        } else {
            // Standart request
            temp = new HashMap<String, String>();

            Map<String, String[]> temp2 = request.getParameterMap();
            for (String s : temp2.keySet()) {
                String[] valueP = temp2.get(s);
                if (valueP.length == 1) {
                    temp.put(s, valueP[0]);
                }
            }
        }

        // Retrieve array
        for (String s : temp.keySet()) {
            if (s.startsWith(searchFor)) {
                int pos = s.indexOf("]", len);
                String index = s.substring(len, pos).trim();
                if (!index.isEmpty()) {
                    int curIndex = getIntFromString(index);
                    while (curIndex >= returnedValue.size()) {
                        returnedValue.add("");
                    }
                    returnedValue.set(curIndex, temp.get(s));
                }
            }
        }

        return returnedValue;
    }

    /**
     * Get Array from request
     *
     * @param request
     * @param value
     * @return
     */
    public static HashMap<String, String> getArrayFromRequest(HttpServletRequest request, String value) {
        HashMap<String, String> returnedValue = new HashMap<String, String>();
        String searchFor = value + "[";
        int len = searchFor.length();

        parseRequest(request);
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        // Build param map
        HashMap<String, String> temp;
        if (isMultipart) {
            temp = (HashMap<String, String>) (request.getAttribute(MULTIPART));
        } else {
            // Standart request
            temp = new HashMap<String, String>();

            Map<String, String[]> temp2 = request.getParameterMap();
            for (String s : temp2.keySet()) {
                String[] valueP = temp2.get(s);
                if (valueP.length == 1) {
                    temp.put(s, valueP[0]);
                }
            }
        }

        // Retrieve array
        for (String s : temp.keySet()) {
            if (s.startsWith(searchFor)) {
                int pos = s.indexOf("]", len);
                String index = s.substring(len, pos).trim();
                if (index.isEmpty()) {
                    index = "" + returnedValue.keySet().size();
                }
                returnedValue.put(index, temp.get(s));
            }
        }

        return returnedValue;
    }

    /**
     * Get Date from request
     *
     * @param request
     * @param value
     * @return
     */
    public static HashMap<String, HashMap<String, String>> get2DArrayFromRequest(HttpServletRequest request, String value) {
        HashMap<String, HashMap<String, String>> returnedValue = new HashMap<String, HashMap<String, String>>();
        String searchFor = value + "[";
        int len = searchFor.length();

        parseRequest(request);
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        // Build param map
        HashMap<String, String> temp;
        if (isMultipart) {
            temp = (HashMap<String, String>) (request.getAttribute(MULTIPART));
        } else {
            // Standart request
            temp = new HashMap<String, String>();

            Map<String, String[]> temp2 = request.getParameterMap();
            for (String s : temp2.keySet()) {
                String[] valueP = temp2.get(s);
                if (valueP.length == 1) {
                    temp.put(s, valueP[0]);
                }
            }
        }

        // Retrieve 2D array
        for (String s : temp.keySet()) {
            if (s.startsWith(searchFor)) {
                int pos1 = s.indexOf("]", len);
                String index1 = s.substring(len, pos1).trim();
                if (index1.isEmpty()) {
                    index1 = "" + returnedValue.keySet().size();
                }

                // Create first index
                HashMap<String, String> temp2;
                if (returnedValue.containsKey(index1)) {
                    temp2 = returnedValue.get(index1);
                } else {
                    temp2 = new HashMap<String, String>();
                    returnedValue.put(index1, temp2);
                }

                int pos2 = s.indexOf("[", pos1 + 1);
                int pos3 = s.indexOf("]", pos2 + 1);
                String index2 = s.substring(pos2 + 1, pos3).trim();
                if (index2.isEmpty()) {
                    index2 = "" + temp2.keySet().size();
                }

                temp2.put(index2, temp.get(s));
            }
        }

        return returnedValue;
    }

    /**
     * GET Client IP Adress
     *
     * @param request
     * @return
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }

        return request.getRemoteAddr();
    }

    /**
     * Check connexion access did not expire
     *
     * @param repository
     * @param request
     * @param code
     * @return
     */
    public static Connexion checkAccess(ConnexionRepository repository, HttpServletRequest request, String code) {
        // Remove existing old connections
        repository.removeOld();
        // Check connection
        Connexion item = repository.getByConnectionId(code);
        if (item != null) {
            String ip = getClientIpAddress(request);
            /*
            if (item.getConnectIp().equals(ip)) {
                // Connection with the same code and the same IP
                repository.expand(item);
            } else {
                // IP changed while connected => refused
                item = null;
            }
            */
        }
        return item;
    }

    /**
     * Check connexion access did not expire
     *
     * @param repository
     * @param request
     * @return
     */
    public static Connexion checkAccess(ConnexionRepository repository, HttpServletRequest request) {
        String code = genericStringFromRequest(request, "connexion");
        return checkAccess(repository, request, code);
    }

    private static void checkEncryptionGenerator() {
        if (bCryptPasswordEncoder == null) {
            int strength = 10;
            bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
        }
    }

    /**
     * Encrypt password
     *
     * @param plainPassword
     * @return
     */
    public static String encryptPassword(String plainPassword) {
        checkEncryptionGenerator();
        if (bCryptPasswordEncoder != null) {
            return bCryptPasswordEncoder.encode(plainPassword);
        }
        return null;
    }

    /**
     * Check password
     *
     * @param plainPassword
     * @param encodedPassword
     * @return
     */
    public static boolean checkPassword(String plainPassword, String encodedPassword) {
        checkEncryptionGenerator();
        if (bCryptPasswordEncoder != null) {
            return bCryptPasswordEncoder.matches(plainPassword, encodedPassword);
        }
        return false;
    }

    /**
     * Check if user can use an action
     *
     * @param userConnection
     * @param action
     * @return
     */
    public static boolean canDoAction(Connexion userConnection, String action) {
        return true;
    }

    /**
     * Build view model for a connected user
     *
     * @param modelName
     * @param menuRepository
     * @param userConnexion
     * @return
     */
    public static ModelAndView getModel(String modelName, Connexion userConnexion) {
        ModelAndView returned = new ModelAndView(modelName);
        if (userConnexion != null) {
            // returned.addObject("menuList", menuRepository.findAvailable(userConnexion.getPersonId().getRoleCollection()));
            returned.addObject("menuList", new ArrayList<Menu>());
            returned.addObject("user", userConnexion);
        } else {
            returned.addObject("menuList", new ArrayList<Menu>());
        }
        return returned;
    }

    /**
     * Get creation item Method from controller
     *
     * @param controller
     * @param methodName
     * @return
     */
    public static Method getMethod(Object controller, String methodName) {
        // Build creation method
        Method method = null;
        if ((controller != null) && (methodName != null) && (!methodName.isEmpty())) {
            try {
                Class[] parameterTypes = new Class[2];
                parameterTypes[0] = List.class;
                parameterTypes[1] = List.class;
                method = controller.getClass().getMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(ApplicationTools.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(ApplicationTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return method;
    }

    /**
     * Create item
     *
     * @param controller
     * @param method
     * @param header
     * @param values
     */
    private static void createItem(Object controller, Method method, List<String> header, List<String> values) {
        if (values.size() == header.size()) {
            try {
                method.invoke(controller, header, values);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ApplicationTools.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(ApplicationTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Import CSV file
     *
     * @param importFile
     * @param controller
     * @param methodName
     */
    public static void importCSV(File importFile, Object controller, String methodName) {
        // Build creation method
        Method method = getMethod(controller, methodName);
        if (method != null) {
            // Read file
            try {
                BufferedReader reader = new BufferedReader(new FileReader(importFile));

                String line = reader.readLine();
                if (line != null) {
                    // Get header
                    List<String> header = new LinkedList<>();
                    StringTokenizer st = new StringTokenizer(line, "\t");
                    while (st.hasMoreElements()) {
                        String name = st.nextToken().trim();
                        header.add(name);
                    }
                    // Get lines
                    line = reader.readLine();
                    while (line != null) {
                        st = new StringTokenizer(line, "\t");
                        List<String> lineValues = new LinkedList<>();
                        while (st.hasMoreElements()) {
                            String value = st.nextToken();
                            lineValues.add(value);
                        }

                        // Create item with values
                        createItem(controller, method, header, lineValues);

                        // Next line
                        line = reader.readLine();
                    }
                }
                reader.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ApplicationTools.class.getName()).log(Level.SEVERE, "No file found", ex);
            } catch (IOException ex) {
                Logger.getLogger(ApplicationTools.class.getName()).log(Level.SEVERE, "Error while reading file", ex);
            }
        }
    }

    /**
     * Get string index in an array
     *
     * @param searchedFor
     * @param array
     * @return
     */
    public static int getStringInArray(String searchedFor, List<String> array) {
        int index = -1;
        for (String s : array) {
            index++;
            if (s.equalsIgnoreCase(searchedFor)) {
                return index;
            }
        }
        return -1;
    }

    /**
     *
     * @param methods
     * @param fieldName
     * @return
     */
    public static Method getSetter(Method[] methods, String fieldName) {
        String setterName = ApplicationTools.buildSetter(fieldName);
        for (Method aMethod : methods) {
            if (aMethod.getName().equals(setterName)) {
                return aMethod;
            }
        }
        return null;
    }

    private static String buildVarname(String prefix, String fieldName) {
        return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * return a Getter name
     *
     * @param fieldName
     * @return
     */
    public static String buildGetter(String fieldName) {
        return buildVarname("get", fieldName);
    }

    /**
     * return a Setter name
     *
     * @param fieldName
     * @return
     */
    public static String buildSetter(String fieldName) {
        return buildVarname("set", fieldName);
    }

    /**
     *
     * @param fullname
     * @return
     */
    public static String[] buildPersonName(String fullname) {
        String[] name = new String[2];
        name[0] = "";
        name[1] = "";

        boolean isLastName = true;
        String[] chrunk = fullname.split(" ");
        for (String str : chrunk) {
            if (!isLastName) {
                name[1] += " " + str;
            } else {
                String toUpper = str.toUpperCase();
                if (toUpper.equals(str)) {
                    name[0] += " " + str;
                } else {
                    isLastName = false;
                    name[1] = str;
                }
            }
        }
        name[0] = name[0].trim();
        name[1] = name[1].trim();

        return name;
    }
    
    public static String correctString(String text){
        String returned=text.replace("à", "a");
        returned=returned.replace("é", "e");
        returned=returned.replace("è", "e");
        returned=returned.replace("ê", "e");
        returned=returned.replace("ù", "u");
        returned=returned.replace("ç", "c");
        returned=returned.replace("â", "a");
        returned=returned.replace("î", "i");
        returned=returned.replace("ô", "o");
        returned=returned.replace("û", "u");
        returned=returned.replace("-", " ");
        returned =returned.replace("'", " ");
        returned =returned.replace("<", " ");
        returned =returned.replace(">", " ");
        returned =returned.toUpperCase();
        return returned;
    }
}
