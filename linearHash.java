import java.util.Random;

@SuppressWarnings("unchecked")

class linearHash<T>{

    //Define constants for the table
    private final int GROWTH_FACTOR = 2;
    private final int MIN_TABLE_SIZE = 10;
    private float MIN_LOAD = (float) 0.2;
    private float MAX_LOAD = (float) 0.6;

    //General hash table variables
    private int size = 0;
    private Random random = new Random();

    //Table 1 specific variables
    private Object[] table;
    private int occupied = 0;
    private int seed = random.nextInt((2^32));

    //Default constructor
    public linearHash(){

        this(10, (float) 0.2, (float) 0.6);

    }

    //Construct with specified min and max load factors
    public linearHash(float min, float max){

        this(10, min, max);

    }

    //Construct with specified size
    public linearHash(int size, float min, float max){

        this.size = size;
        this.table = new Object[size];
        this.MIN_LOAD = min;
        this.MAX_LOAD = max;

    }

    //Return the load factor of the hash table
    public float getLoad(){
        
        return (float) occupied/size;

    };

    //Insert allowing resize bypass
    public Boolean insert(T o, Boolean bypass){

        Boolean exists = false;

        int hash = hashFunc(o);
        while(table[hash] != null && hash < (size-1)) exists |= table[hash++].equals(o);
        if(hash<(size-1) && !exists){
            table[hash] = o;
            occupied++;
        }
        else if(exists) return false;
        else resize(o);

        if(!bypass) resize();

        return true;

    }

    //Return true if the object is added, false if the element already exists or an error occurs
    public Boolean insert(T o){

        return insert(o, false);
        
    }

    //Delete the given key from the table
    public Boolean delete(T o){

        int loc = get(o);
        if(loc == -1) return false;
        table[loc] = null;
        resize();
        occupied--;

        return true;
        
    }

    //Return the index of given key
    public int get(T o){

        int hash = hashFunc(o);
        if(table[hash] == null) return -1;
        while(!table[hash].equals(o) && hash<(size-1)) hash++;
        if(hash<(size-1)) return hash;
        return -1;

    }

    //Return hash table as an object array
    protected Object[] getHashObj(){
        
        Object[] newHashObj = new Object[4];

        newHashObj[0] = table;
        newHashObj[1] = occupied;
        newHashObj[2] = seed;
        newHashObj[3] = size;

        return newHashObj;

    }

    //Calculate the hashes for the object using a given seed value
    public int hashFunc(T o){

        int hash = o.hashCode();
        Random rand = new Random();
        rand.setSeed(hash*seed);
        hash = rand.nextInt();

        return Math.abs(hash%size);

    }

    //Resize the table when load factor exceeds the given bounds
    private void resize(){

        resize(null);

    }

    //Resize the table when load factor exceeds the given bounds
    private void resize(T o){

        float load = getLoad();

        if(load >= MAX_LOAD || o != null){
            linearHash<T> newHash = new linearHash<>(size*GROWTH_FACTOR, MIN_LOAD, MAX_LOAD);
            for(int i = 0; i<size; i++) if(table[i] != null) newHash.insert((T) table[i], true);
            if(o != null) newHash.insert((T) o);
            copy(newHash);
        }
        else if(load < MIN_LOAD && size > MIN_TABLE_SIZE){
            linearHash<T> newHash = new linearHash<>(size/GROWTH_FACTOR, MIN_LOAD, MAX_LOAD);
            for(int i = 0; i<size; i++) if(table[i] != null) newHash.insert((T) table[i], true);
            copy(newHash);
        }

    }

    //Copy values from one linearHash to another
    public void copy(linearHash h){

        Object[] toCopy = h.getHashObj();

        this.table = (Object[]) toCopy[0];
        this.occupied = (int) toCopy[1];
        this.seed = (int) toCopy[2];
        this.size = (int) toCopy[3];

    }

    //Print the table
    @Override
    public String toString(){

        String result = "[ ";

        for(int i = 0; i<size; i++){
            if(table[i] == null) result += "_ ";
            else result += table[i].toString()+" ";
        }

        result += "]";

        return result;

    }

}