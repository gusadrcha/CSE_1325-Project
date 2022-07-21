package files;

public class CsvReadException extends Exception
{
    private String data;

    public CsvReadException() {}

    public CsvReadException(String data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "CsvReadException.java : " + data + " -- INVALID LINE";
    }
}

