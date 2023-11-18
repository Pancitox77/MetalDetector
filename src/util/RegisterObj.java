package util;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

public class RegisterObj implements Serializable {
    private String user,detector,object,material,description,place,hour;
    private int id;
    private LocalDate date;
    private String[] imagesPath;

    public RegisterObj(){
        user = "Invitado"; detector = "A simple vista"; object = "Desconocido";
        material = "Desconocido"; description = ""; place = ""; date = LocalDate.now();
        id = 0; hour = "00:00"; imagesPath = new String[3];        
    }

    /* Setters */
    public void setUser(String user) { this.user = user; }
    public void setDetector(String detector) { this.detector = detector; }
    public void setObject(String object){ this.object = object; }
    public void setMaterial(String material){ this.material = material; }
    public void setDescription(String description){ this.description = description; }
    public void setPlace(String place){ this.place = place; }
    public void setId(int id){ this.id = id; }
    public void setDate(LocalDate date){ this.date = date; }
    public void setImagesPath(String[] imagesPath){ this.imagesPath = imagesPath; }
    public void setHour(String hour){ this.hour = hour; }
    

    /* Getters */
    public String getUser() { return user; }
    public String getDetector() { return detector; }
    public String getObject() { return object; }
    public String getMaterial(){ return material; }
    public String getDescription(){ return description; }
    public String getPlace(){ return place; }
    public int getId(){ return id; }
    public LocalDate getDate(){ return date; }
    public String[] getImagesPath(){ return imagesPath; }
    public String getHour(){ return hour; }

    @Override
    public String toString() {
        return "RegisterObj [user=" + user + ", detector=" + detector + ", object=" + object
            + ", material=" + material + ", description=" + description + ", place=" + place
            + ", id=" + id + ", date=" + date + ", hour=" + hour
            + ", imagesPath=" + Arrays.toString(imagesPath) + "]";
    }


}
