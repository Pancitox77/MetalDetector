package util;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FileSerializer {
    private File file;
    private ArrayList<RegisterObj> readedRegister;
    private ArrayList<String> users;
    private ArrayList<String> objects;
    private ArrayList<String> materials;
    private ArrayList<String> desciptions;
    private ArrayList<String> detectors;
    private ArrayList<String> places;
    private ArrayList<String> hours;
    private ArrayList<LocalDate> dates;

    private static final String USER_DATA_DIR = "src/data/user";

    public FileSerializer() {
        this.file = new File("src/data/registers.ser");
        try {
            if (!this.file.exists()) {
                this.file.createNewFile();
                this.write(new ArrayList<>());
            }
        } catch (IOException ioe) {
            System.out.println("IOError:" + getClass());
            ioe.printStackTrace();
        }
    }

    /* Read / Write funcs */
    public void read() {
        /* Read ArrayList obj with RegisterObj's and save it on 'readedRegister' */
        this.readedRegister = new ArrayList<>();
        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(this.file))) {
            // Read the info
            this.readedRegister = (ArrayList<RegisterObj>) objIn.readObject();
            this.organize();

        } catch (FileNotFoundException fileNot) {
            System.out.println("Not found Error: File not found");
        } catch (NotSerializableException notSerial) {
            System.out.println("Serial Error: Not serializable");
        } catch (EOFException nullPoint) {
            System.out.println("Empty file {" + nullPoint.getMessage() + "}");
        } catch (Exception e) {
            System.out.println("Error:");
            e.printStackTrace();
        }
    }

    public void write(ArrayList<RegisterObj> registersArr) {
        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(this.file))) {
            // Write the info
            objOut.writeObject(registersArr);

        } catch (FileNotFoundException fileNot) {
            System.out.println("Not found Error: File not found");
        } catch (NotSerializableException notSerial) {
            System.out.println("Serial Error: Not serializable");
        } catch (IOException ioe) {
            System.out.println("IOError: " + ioe.getMessage());
        } catch (Exception e) {
            System.out.println("Another Error: " + e.getMessage());
        }
    }

    /* Organize funcs */
    public void addRegister(RegisterObj register) {
        this.readedRegister.add(register);
        this.write(readedRegister);
        this.read();
    }

    public void removeRegister(RegisterObj register) {
        // delete files (photos)
        String[] urls = register.getImagesPath();

        File a = new File(USER_DATA_DIR + new File(urls[0]).getName());
        File b = new File(USER_DATA_DIR + new File(urls[1]).getName());
        File c = new File(USER_DATA_DIR + new File(urls[2]).getName());

        try {
            Files.deleteIfExists(Path.of(a.toURI()));
            Files.deleteIfExists(Path.of(b.toURI()));
            Files.deleteIfExists(Path.of(c.toURI()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.readedRegister.remove(register);
        this.write(readedRegister);
        this.read();
    }

    public void removeRegister(int index) {
        // delete files (photos)
        String[] urls = readedRegister.get(index).getImagesPath();
        try {
            Files.delete(Path.of(new File(urls[0]).toURI()));
            Files.delete(Path.of(new File(urls[0]).toURI()));
            Files.delete(Path.of(new File(urls[0]).toURI()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.readedRegister.remove(index);
        this.write(readedRegister);
        this.read();
    }

    public void updateRegister(RegisterObj register) {
        for (int i = 0; i < readedRegister.size(); i++) {
            if (readedRegister.get(i).equals(register)) {
                readedRegister.set(i, register);
                break;
            }
        }
        this.write(readedRegister);
        this.read();
    }

    public void clearRegister() {
        try {
            this.file.createNewFile();
            this.write(new ArrayList<RegisterObj>());
            this.read();

            // Delete all user photos
            Stream<Path> fileStream = Files.list(Path.of(new File(USER_DATA_DIR).toURI()));
            fileStream.forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void organize() {
        ArrayList<String[]> images;
        users = new ArrayList<>();
        dates = new ArrayList<>();
        objects = new ArrayList<>();
        materials = new ArrayList<>();
        desciptions = new ArrayList<>();
        detectors = new ArrayList<>();
        places = new ArrayList<>();
        hours = new ArrayList<>();
        images = new ArrayList<>();

        if (!readedRegister.isEmpty()) {
            for (int i = 0; i < readedRegister.size(); i++) {
                users.add(readedRegister.get(i).getUser());
                dates.add(readedRegister.get(i).getDate());
                objects.add(readedRegister.get(i).getObject());
                materials.add(readedRegister.get(i).getMaterial());
                desciptions.add(readedRegister.get(i).getDescription());
                detectors.add(readedRegister.get(i).getDetector());
                places.add(readedRegister.get(i).getPlace());
                hours.add(readedRegister.get(i).getHour());
                images.add(readedRegister.get(i).getImagesPath());
            }
        }
    }

    /* Setters */
    public void setFile(File file) {
        this.file = file;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public void setDates(ArrayList<LocalDate> dates) {
        this.dates = dates;
    }

    public void setObjects(ArrayList<String> objects) {
        this.objects = objects;
    }

    public void setMaterials(ArrayList<String> materials) {
        this.materials = materials;
    }

    public void setDesciptions(ArrayList<String> desciptions) {
        this.desciptions = desciptions;
    }

    public void setDetectors(ArrayList<String> detectors) {
        this.detectors = detectors;
    }

    public void setPlaces(ArrayList<String> places) {
        this.places = places;
    }

    public void setHours(ArrayList<String> hours) {
        this.hours = hours;
    }

    /* Getters */
    public File getFile() {
        return file;
    }

    public ArrayList<RegisterObj> getRegisters() {
        return readedRegister;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public ArrayList<LocalDate> getDates() {
        return dates;
    }

    public ArrayList<String> getObjects() {
        return objects;
    }

    public ArrayList<String> getMaterials() {
        return materials;
    }

    public ArrayList<String> getDesciptions() {
        return desciptions;
    }

    public ArrayList<String> getDetectors() {
        return detectors;
    }

    public ArrayList<String> getPlaces() {
        return places;
    }

    public ArrayList<String> getHours() {
        return hours;
    }
}
