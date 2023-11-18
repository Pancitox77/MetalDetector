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
import java.util.ArrayList;
import java.util.HashMap;

public class OptionSerializer {
    private File file;
    private HashMap<String,ArrayList<String>> readedMap;
    public final String[] keys = new String[]{"Objetos","Materiales","Detectores","Lugares","Usuarios"};

    
    public OptionSerializer(){
        this.file = new File("src/data/options.ser");
        try {
            if (!this.file.exists()){
                this.file.createNewFile();

                HashMap<String,ArrayList<String>> hash = new HashMap<>(); //KEYS, same as 'getNameEdit()'
                hash.put("Objetos", new ArrayList<>());
                hash.put("Materiales", new ArrayList<>());
                hash.put("Detectores", new ArrayList<>());
                hash.put("Lugares", new ArrayList<>());
                hash.put("Usuarios", new ArrayList<>());

                this.write(hash);
            }
        } catch (IOException ioe){ System.out.println("IOError:"+getClass()); ioe.printStackTrace(); }
    }

    /* Read/Write */
    public void read(){
        this.readedMap = new HashMap<>();
        try(ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(file))) {
            //Read the info
            this.readedMap = (HashMap<String,ArrayList<String>>)objIn.readObject();

        }
        catch (FileNotFoundException fileNot){ System.out.println("Not found Error: File not found"); }
        catch (NotSerializableException notSerial){ System.out.println("Serial Error: Not serializable"); }
        catch (EOFException nullPoint){
            System.out.println("Empty file {"+nullPoint.getMessage()+"}");
        }
        catch (Exception e){ System.out.println("Error:"); e.printStackTrace(); }
    }

    public void write(HashMap<String,ArrayList<String>> optionsMap){
        try(ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(file))) {
            //Write the info
            objOut.writeObject(optionsMap);
        }
        catch (FileNotFoundException fileNot){ System.out.println("Not found Error: File not found"); }
        catch (IOException ioe){ System.out.println("IOError: "+ioe.getMessage()); }
    }


    /* Utility */
    public ArrayList<String> getArray(String key){
        this.read();
        for(String i: this.keys){ if (i.equals(key)){return readedMap.get(key); } }
        return null;
    }

    public void setArray(String key, ArrayList<String> array){
        for(String i: this.keys){
            if (i.equals(key)){
                readedMap.put(key, array);
                this.write(readedMap);
                break;
            }
        }
    }

    public void clearOptions(){
        try {
            //Default values
            this.readedMap = new HashMap<String,ArrayList<String>>();
            this.readedMap.put("Objetos", new ArrayList<String>());
            this.readedMap.put("Materiales", new ArrayList<String>());
            this.readedMap.put("Detectores", new ArrayList<String>());
            this.readedMap.put("Lugares", new ArrayList<String>());
            this.readedMap.put("Usuarios", new ArrayList<String>());
            
            this.file.createNewFile();
            this.write(readedMap);
            this.read();
        
        } catch(IOException ex){}
    }


    /* Setters */
    public void setFile(File file){ this.file = file; }
    public void setReadedMap(HashMap<String, ArrayList<String>> readedMap){ this.readedMap = readedMap; }


    /* Getters */   
    public File getFile(){ return file; }
    public HashMap<String, ArrayList<String>> getReadedMap(){ return readedMap; }
}
