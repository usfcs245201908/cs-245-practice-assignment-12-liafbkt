import java.util.ArrayList;

public class Hashtable {
    ArrayList<HashNode> bucket;
    int entries = 0;
    int size = 3000;
    double LOAD_THRESHOLD = 0.7;
    class HashNode{
        String key;
        String value;
        HashNode next;

        public HashNode(String key, String value){
            this.key = key;
            this.value = value;
            next = null;
        }
    }
    public Hashtable(){
        bucket = new ArrayList<HashNode>();
        for(int i = 0; i < size; i++){
            bucket.add(null);
        }
    }

    /**
     * Get the hashcode of the key
     * @param key - key
     * @return the hash code of the key
     */
    public int getHash(String key){
        return Math.abs(key.hashCode()%size);
    }


    /**
     * add a key pair with value into the hashtable
     * @param key key
     * @param value value
     */
    public void put(String key, String value){
        HashNode head = bucket.get(getHash(key));
        if( head == null){
            bucket.set(getHash(key), new HashNode(key, value));
        }else{
            while(head != null){
                if(head.key == key){
                    head.value = value;
                    return;
                }
                head = head.next;
            }
            HashNode node = new HashNode(key, value);
            node.next = bucket.get(getHash(key));
            bucket.set(getHash(key),node);
        }

        ++entries;
        if( (entries*1.0 / size) >= LOAD_THRESHOLD){
            // Increase #bucket
            ArrayList<HashNode> temp = bucket;
            bucket = new ArrayList<HashNode>();
            size*=2;
            for(int i = 0; i < size; i++){
                bucket.add(null);
            }
            // Rehash each item in table
            for(HashNode headNode : temp){
                while(headNode != null){
                    put(headNode.key, headNode.value);
                    headNode = headNode.next;
                }
            }
        }
    }

    /**
     * see if the hashtable contains the key
     * @param key - key
     * @return - if the hashtable contains the key
     */
    public boolean containsKey(String key){
        HashNode head = bucket.get(getHash(key));
        while( head != null){
            if(head.key == key){
                return true;
            }
            head = head.next;
        }
        return false;
    }


    /**
     * return the value of the key
     * @param key - key
     * @return the value of the key
     */
    public String get(String key){
        HashNode head = bucket.get(getHash(key));
        while( head != null){
            if(head.key == key){
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    /**
     * remove the key from the hashtable; return null if the key does not exist
     * @param key - key
     * @return the value of the key or null if the key does not exist
     */
    public String remove(String key){
        HashNode head = bucket.get(getHash(key));
        if (head != null){
            if( head.key == key){
                bucket.set( getHash(key), head.next);
                return head.value;
            }else{
                HashNode prev = head;
                HashNode current;
                while( prev.next != null){
                    current = prev.next;
                    if(current.key == key){
                        prev.next = current.next;
                        return current.value;
                    }
                }
// decrease entries;
            }
            entries--;
        }
        return null;
    }


}
