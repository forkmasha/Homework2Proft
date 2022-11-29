package task2;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Record {
    private String DateTime;
    private String FirstName;
    private String LastName;
    private String Type;
    private double FineAmount;

    public Record(String DateTime, String FirstName, String LastName, String type, double FineAmount) {
        this.DateTime = DateTime;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Type = type;
        this.FineAmount = FineAmount;
    }


    public void setDateTime(String dateTime) {
        this.DateTime = dateTime;
    }


    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }


    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public double getFineAmount() {
        return FineAmount;
    }

    public void setFineAmount(double fineAmount) {
        this.FineAmount = fineAmount;
    }

    @Override
    public String toString() {
        return "Record [date_time=" + DateTime + ", first_name=" + FirstName + ", last_name=" + LastName + ", type="
                + Type + ", fine_amount=" + FineAmount + "]";
    }


    public static List<String> getTypes(List<Record> records) {
        List<String> types = new ArrayList<>();
        for (Record record : records) {
            if (!types.contains(record.getType())) {
                types.add(record.getType());
            }
        }
        return types;
    }

    public static List<Record> getRecordsByType(List<Record> records, String type) {
        List<Record> recordsByType = new ArrayList<>();
        for (Record record : records) {
            if (Objects.equals(record.getType(), type)) {
                recordsByType.add(record);
            }
        }
        return recordsByType;
    }



    public static Comparator<Record> fine_amountComparator = (o1, o2) -> (int) (o2.getFineAmount() - o1.getFineAmount());




}

