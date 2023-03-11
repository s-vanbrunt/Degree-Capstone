package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Class to create Appointment objects with id, title, description, location, start time, end time,
 * Customer, User, and Contact fields and related methods.
 *
 * @author Scott VanBrunt
 */
public class Appointment {
    private int id;
    private String title;
    private String desc;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDate apptDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime end;
    private Customer customer;
    private int custId;
    private User user;
    private int userId;
    private Contact contact;
    private String contactName;

    /**
     * Default constructor to assign default values to fields on object creation.
     */
    public Appointment(){
        this.id = 0;
        this.title = null;
        this.desc = null;
        this.location = null;
        this.type = null;
        this.start = null;
        this.apptDate = null;
        this.startTime = null;
        this.end = null;
        this.endTime = null;
        this.customer = null;
        this.custId = 0;
        this.user = null;
        this.userId = 0;
        this.contact = null;
        this.contactName = null;
    }

    /**
     * Overloaded constructor to assign fields values based on arguments.
     *
     * @param id int to set Appointment id.
     * @param title String to set Appointment title.
     * @param desc String to set Appointment desc.
     * @param location String to set Appointment location.
     * @param type String to set Appointment type.
     * @param start LocalDateTime to set Appointment start.
     * @param end LocalDateTime to set Appointment send.
     * @param customer Customer to set Appointment Customer.
     * @param user User to set Appointment User.
     * @param contact Contact to set Appointment Contact.
     */
    public Appointment(int id, String title, String desc, String location, String type, LocalDateTime start, LocalDateTime end, Customer customer, User user, Contact contact){
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.type = type;
        this.start = start;
        this.apptDate = start.toLocalDate();
        this.startTime = start.toLocalTime();
        this.end = end;
        this.endTime = end.toLocalTime();
        this.customer = customer;
        this.custId = customer.getId();
        this.user = user;
        this.userId = user.getId();
        this.contact = contact;
        this.contactName = contact.getName();
    }

    /**
     *
     * @return int of Appointment id.
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id int to set Appointment id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * return String of Appointment title.
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title String to set Appointment title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return String of Appointment description.
     */
    public String getDesc() {
        return desc;
    }

    /**
     *
     * @param desc String to set Appointment description.
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     *
     * @return String of Appointment location.
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location String to set Appointment location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return String of Appointment type.
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type String to set Appointment type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return LocalDateTime of Appointment start.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     *
     * @param start LocalDateTime to set Appointment start.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
        this.apptDate = start.toLocalDate();
        this.startTime = start.toLocalTime();
    }

    /**
     *
     * @return LocalDateTime of Appointment end.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     *
     * @param end LocalDateTime to set Appointment end.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
        this.endTime = end.toLocalTime();
    }

    /**
     *
     * @return Customer of Appointment customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     *
     * @param customer Customer to set Appointment customer.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.custId = customer.getId();
    }

    /**
     *
     * @return User of Appointment user.
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user User to set Appointment user.
     */
    public void setUser(User user) {
        this.user = user;
        this.userId = user.getId();
    }

    /**
     *
     * @return Contact of Appointment contact.
     */
    public Contact getContact() {
        return contact;
    }

    /**
     *
     * @param contact Contact to set Appointment contact.
     */
    public void setContact(Contact contact) {
        this.contact = contact;
        this.contactName = contact.getName();
    }

    /**
     *
     * @return LocalDate for Start Date.
     */
    public LocalDate getApptDate() {
        return apptDate;
    }

    /**
     *
     * @return LocalTime for Start Time.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     *
     * @return LocalTime for End Time.
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     *
     * @return int for Customer Id.
     */
    public int getCustId() {
        return custId;
    }

    /**
     *
     * @return int for User Id.
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @return String for Contact Name.
     */
    public String getContactName() {
        return contactName;
    }
}
