import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Implements the PictureDatasource Interface storing the data in
 * Character Separated Values (CSV) format, where each line consists of a record
 * whose fields are separated by the DELIMITER value ";".<br>
 * See the example file: db/picture-data.csv
 */
public class FilePictureDatasource implements PictureDatasource {
    // Charset to use for file encoding.
    protected static final Charset CHARSET = StandardCharsets.UTF_8;
    // Delimiter to separate record fields on a line
    protected static final String DELIMITER = ";";
    // Date format to use for date-specific record fields
    protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private final File file;
    File tempfile;

    /**
     * Creates the FilePictureDatasource object with the given file path as a datafile.
     * Creates the file if it does not exist.
     * Also creates an empty temp file for write operations.  The temp file is not used
     * and can be removed.
     *
     * @param filepath of the file to use as a database file.
     * @throws IOException if accessing or creating the file fails
     */
    public FilePictureDatasource(String filepath) throws IOException {
        this.file = new File(filepath);
        if (file.createNewFile()) {
            System.out.println("file successfully created");
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(Picture picture) {
        List<Long> takenIds = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file))) {
            String line;
            while (!((line = bufferedReader.readLine()) == null)) {
                String[] pictureValues = line.split(DELIMITER);
                takenIds.add(Long.parseLong(pictureValues[0])); // Changed to Long
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (takenIds.isEmpty()) {
            picture.setId(1L); // start with ID 1 if file is empty
        } else {
            takenIds.sort(Comparator.reverseOrder());
            long newId = takenIds.getFirst() + 1;
            picture.setId(newId);
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.file, true))) { //append mode
            bufferedWriter.write(preparePrint(picture));
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Picture picture) throws RecordNotFoundException {
        List<String> linesInFile = new ArrayList<>();
        boolean replaced = false;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file))) {
            String line;
            while (!((line = bufferedReader.readLine()) == null)) {
                String[] pictureValues = line.split(DELIMITER);
                if (picture.getId() == Long.parseLong(pictureValues[0])) { // Changed to Long
                    replaced = true;
                    linesInFile.add(preparePrint(picture));
                } else {
                    linesInFile.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.file))) {
            if (replaced) {
                for (String line : linesInFile) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            } else {
                throw new RecordNotFoundException("No Id has matched the given one, no updates were made");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Picture picture) throws RecordNotFoundException {
        List<String> linesInFile = new ArrayList<>();
        boolean found = false;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file))) {
            String line;
            while (!((line = bufferedReader.readLine()) == null)) {
                String[] pictureValues = line.split(DELIMITER);
                if (picture.getId() == Long.parseLong(pictureValues[0])) { // Changed to Long
                    found = true;
                } else {
                    linesInFile.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.file))) {
            if (found) {
                for (String line : linesInFile) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            } else {
                throw new RecordNotFoundException("No Id has matched the given one, no updates were made");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() {
        int count = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file))) {
            while (bufferedReader.readLine() != null) { //simplified
                count++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Picture> findById(long id) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file))) {
            String line;
            while (!((line = bufferedReader.readLine()) == null)) {
                String[] stringToPicture = line.split(DELIMITER);
                if (Long.parseLong(stringToPicture[0]) == id) { // Changed to Long
                    return Optional.ofNullable(createPicture(stringToPicture));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Picture> findAll() {
        List<Picture> pictureList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file))) {
            String line;
            while (!((line = bufferedReader.readLine()) == null)) {
                String[] stringToPicture = line.split(DELIMITER);
                pictureList.add(createPicture(stringToPicture));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pictureList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Picture> findByPosition(float longitude, float latitude, float deviation) {
        List<Picture> pictureList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file))) {
            String line;
            while (!((line = bufferedReader.readLine()) == null)) {
                String[] pictureValues = line.split(DELIMITER);
                float fileLongitude = Float.parseFloat(pictureValues[2]);
                float fileLatitude = Float.parseFloat(pictureValues[3]);
                if ((fileLongitude < (longitude + deviation) && fileLongitude > (longitude - deviation))
                    && (fileLatitude < (latitude + deviation) && fileLatitude > (latitude - deviation))) {
                    pictureList.add(createPicture(pictureValues));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pictureList;
    }

    public Picture createPicture(String[] stringToPicture) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        Date date = null;
        URL url = null;
        try {
            date = formatter.parse(stringToPicture[1]);
            try {
                url = new URL(stringToPicture[5]);
            } catch (MalformedURLException e) {
                System.err.println("Malformed URL: " + stringToPicture[5] + " setting to null");
                url = null; //set url to null
            }

        } catch (ParseException e) {
            System.err.println("Error parsing the date string: " + e.getMessage() + " for input: " + stringToPicture[1]);
            throw new IllegalArgumentException("Invalid date format in record: " + Arrays.toString(stringToPicture), e); // throw exception
        }
        try {
            return new Picture(Long.parseLong(stringToPicture[0]), url, date, stringToPicture[4], Float.parseFloat(stringToPicture[2]), Float.parseFloat(stringToPicture[3]));
        }
        catch (NumberFormatException e){
            System.err.println("Error parsing the record: " + Arrays.toString(stringToPicture));
            throw new IllegalArgumentException("Invalid number format in record: " + Arrays.toString(stringToPicture), e);
        }

    }

    public String preparePrint(Picture picture) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String urlString = (picture.getUrl() != null) ? picture.getUrl().toString() : null;
        return picture.getId() + DELIMITER +
            formatter.format(picture.getDate()) + DELIMITER +
            picture.getLongitude() + DELIMITER +
            picture.getLatitude() + DELIMITER +
            picture.getTitle() + DELIMITER +
            urlString;
    }
}
