package taojava.util;

import java.io.PrintWriter;

/**
 * A simple set of experimetns using our new hash tables.
 *
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 */
public class HashTableExpt
{
  // +-------------+-----------------------------------------------------
  // | Experiments |
  // +-------------+

  /**
   * Explore what happens when we use set with a repeated key.
   */
  public static void repeatedSetExpt(PrintWriter pen,
                                     HashTable<String, String> dict)
  {
    dict.reportBasicCalls(true);
    dict.set("alpha", "alpha");
    dict.dump(pen);
    dict.set("beta", "beta");
    dict.dump(pen);
    dict.set("bravo", "bravo");
    dict.dump(pen);
    dict.set("beta", "bravo");
    dict.dump(pen);
    dict.reportBasicCalls(false);
  } // repeatedSetExpt(PrintWriter, HashTable)

  /**
   * Explore what happens when we use two keys that map to the
   * same location.
   */
  public static void matchingKeysExpt(PrintWriter pen,
                                      HashTable<String, String> dict)
  {
    pen.println("Setting anteater");
    dict.set("anteater", "anteater");
    pen.println("Getting buffalo");
    try
      {
        pen.println(dict.get("buffalo"));
      }
    catch (Exception e)
      {
        pen.println("Failed ... " + e);
      } // try/catch
  } // matchingKeysExpt(PrintWriter, HashTable)

  /**
   * Explore what happens when we use set with a wide variety of
   * key/value pairs.
   */
  public static void setExpt(PrintWriter pen, HashTable<String, String> dict)
  {
    // Word list stolen from some unit test that SamR` wrote earlier.
    String[] words =
        { "aardvark", "anteater", "antelope", "bear", "bison", "buffalo",
         "chinchilla", "cat", "dingo", "elpehant", "eel", "flying squirrel",
         "fox", "goat", "gnu", "goose", "hippo", "horse", "iguana",
         "jackalope", "kestrel", "llama", "moose", "mongoose", "nilgai",
         "orangutan", "opossum", "red fox", "snake", "tarantula", "tiger",
         "vicuna", "vulture", "wombat", "yak", "zebra", "zorilla" };
    int numwords = words.length;
    for (int i = 0; i < numwords; i++)
      {
        // dict.dump(pen);
        dict.set(words[i], words[i]);
        for (int j = 0; j <= i; j++)
          {
            try
              {
                String str = dict.get(words[j]);
                if (!str.equals(words[j]))
                  {
                    pen.println("After setting " + words[i] + ", " + words[j]
                                + " no longer yields itself.");
                    dict.dump(pen);
                    return;
                  } // if we didn't get the expected value.
                pen.println("Passed? " + i);
              }
            catch (Exception e)
              {
                pen.println("After setting " + words[i] + ", " + words[j]
                            + " is no longer in the dictionary.");
                return;
              } // try/catch
          } // for j
      } // for i
  } // setExpt(PrintWriter, HashTable)

  /**
   * Explore what happens when we remove elements.
   */
  public static void
    removeExpt(PrintWriter pen, HashTable<String, String> dict)
  {
    // STUB
  } // removeExpt(PrintWriter, HashTable)
  
  // +------+------------------------------------------------------------
  // | Main |
  // +------+

  /**
   * Do whatever experiments seem reasonable.
   */
  public static void main(String[] args)
  {
    // Create our normal output mechanism.
    final PrintWriter pen = new PrintWriter(System.out, true);
    // Convert the PrintWriter to a Reporter.
    Reporter rept = new Reporter()
      {
        public void report(String str)
        {
          pen.println("*** " + str);
        } // report(String)
      }; // new Reporter()
    // Create a new hash table
    HashTable<String, String> dict = new HashTable<String, String>(rept);

    // Most of the time, we don't care about the basic calls
    dict.reportBasicCalls(false);

    // Conduct some of the experiments
 //  repeatedSetExpt(pen, dict);
  //   matchingKeysExpt(pen, dict);
     setExpt(pen, dict);
    // removeExpt(pen, dict);
  } // main(String[])

} // class HashTableExpt
