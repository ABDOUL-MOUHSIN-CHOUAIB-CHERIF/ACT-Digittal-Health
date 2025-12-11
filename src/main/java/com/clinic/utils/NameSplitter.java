package com.clinic.utils;


public class NameSplitter {

    // Private constructor - utility class shouldn't be instantiated
    private NameSplitter() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Split full name into first name and last name
     * @param fullName The full name to split
     * @return String array with [firstName, lastName]
     */
    public static String[] splitFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return new String[]{"", ""};
        }

        String trimmedName = fullName.trim().replaceAll("\\s+", " ");
        String[] nameParts = trimmedName.split(" ");

        // Handle single name
        if (nameParts.length == 1) {
            return new String[]{nameParts[0], ""};
        }

        // Handle two names: "John Doe" -> ["John", "Doe"]
        if (nameParts.length == 2) {
            return new String[]{nameParts[0], nameParts[1]};
        }

        // Handle three names: "John Michael Doe" -> ["John", "Michael Doe"]
        if (nameParts.length == 3) {
            return new String[]{nameParts[0], nameParts[1] + " " + nameParts[2]};
        }

        // Handle four or more names (common in Cameroon):
        // "Abdoul Mouhsin Chouaib Cherif" -> ["Abdoul Mouhsin", "Chouaib Cherif"]
        if (nameParts.length >= 4) {
            StringBuilder firstName = new StringBuilder();
            StringBuilder lastName = new StringBuilder();

            // First half goes to first name
            for (int i = 0; i < nameParts.length / 2; i++) {
                if (firstName.length() > 0) firstName.append(" ");
                firstName.append(nameParts[i]);
            }

            // Second half goes to last name
            for (int i = nameParts.length / 2; i < nameParts.length; i++) {
                if (lastName.length() > 0) lastName.append(" ");
                lastName.append(nameParts[i]);
            }

            return new String[]{firstName.toString(), lastName.toString()};
        }

        // Fallback: first word as first name, rest as last name
        String firstName = nameParts[0];
        StringBuilder lastName = new StringBuilder();
        for (int i = 1; i < nameParts.length; i++) {
            if (lastName.length() > 0) lastName.append(" ");
            lastName.append(nameParts[i]);
        }

        return new String[]{firstName, lastName.toString()};
    }

    /**
     * Extract first name from full name
     */
    public static String getFirstName(String fullName) {
        String[] names = splitFullName(fullName);
        return names[0];
    }

    /**
     * Extract last name from full name
     */
    public static String getLastName(String fullName) {
        String[] names = splitFullName(fullName);
        return names[1];
    }

    /**
     * Format first and last name for display
     */
    public static String formatForDisplay(String firstName, String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            return firstName;
        }
        return firstName + " " + lastName;
    }

    /**
     * Remove titles/honorifics from name
     */
    public static String removeTitles(String fullName) {
        if (fullName == null) return "";

        String[] titles = {"Dr.", "Dr ", "Prof.", "Prof ", "Mr.", "Mr ",
                "Mrs.", "Mrs ", "Ms.", "Ms ", "Eng.", "Eng "};

        String cleanedName = fullName;
        for (String title : titles) {
            if (cleanedName.toLowerCase().startsWith(title.toLowerCase())) {
                cleanedName = cleanedName.substring(title.length()).trim();
                break;
            }
        }

        return cleanedName;
    }

    /**
     * Split full name with title removal
     */
    public static String[] splitFullNameClean(String fullName) {
        String cleanedName = removeTitles(fullName);
        return splitFullName(cleanedName);
    }
}