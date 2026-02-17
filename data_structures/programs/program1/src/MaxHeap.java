import java.util.ArrayList;
import java.util.Collection;

public class MaxHeap
{
   private ArrayList<Student> students;
   
   public MaxHeap(int capacity)
   {
      students = new ArrayList<Student>(capacity);
   }
      
   public MaxHeap(Collection<Student> collection)
   {
      students = new ArrayList<Student>(collection);

      for (int i = 0; i < students.size(); i++) {
         students.get(i).setIndex(i);
      }
      for(int i = size()/2 - 1; i >= 0; i--)
      {
         maxHeapify(i);
      }
   }
   
   public Student getMax()
   {
      if(size() < 1)
      {
         throw new IndexOutOfBoundsException("No maximum value:  the heap is empty.");
      }
      return students.get(0);
   }
   
   public Student extractMax()
   {
      Student value = getMax();
      swap(0, size() - 1);
      students.remove(size() - 1);
      if (size() > 0) {
         maxHeapify(0);
      }
      return value;
   }
    
   public int size()
   {
      return students.size();
   }
   
   public void insert(Student elt)
   {
      //Please write me.  I should add the given student into the heap,
	  //following the insert algorithm from the videos.

      // Insert @ end of array
      students.add(elt);
      elt.setIndex(students.size() - 1);
      checkTowardsRoot(elt.getIndex());
   }
   
   public void addGrade(Student elt, double gradePointsPerUnit, int units)
   {
      //Please write me.  I should change the student's gpa (using a method
	  //from the student class), and then adjust the heap as needed using
	  //the changeKey algorithm from the videos.

      // PART 1 CODE ------------------------------------------------------
//      elt.addGrade(gradePointsPerUnit, units);
//      int index = students.indexOf(elt); // INDEXOF ONLY ALLOWED ONCE
//      checkTowardsRoot(index);
//      maxHeapify((index));

      // PART 2 CODE -------------------------------------------------------
      double old = elt.gpa();
      elt.addGrade(gradePointsPerUnit, units);
      if (elt.gpa() > old) {
         checkTowardsRoot(elt.getIndex());
      } else {
         maxHeapify(elt.getIndex());
      }
   }
   
   private int parent(int index)
   {
      return (index - 1)/2;
   }
   
   private int left(int index)
   {
      return 2 * index + 1;
   }
   
   private int right(int index)
   {
      return 2 * index + 2;
   }
   
   private void swap(int from, int to)
   {
      Student val = students.get(from);
      students.set(from,  students.get(to));
      students.set(to,  val);
      val.setIndex(to);
      students.get(from).setIndex(from);
   }

   private void checkTowardsRoot(int index) {
      while (index > 0 && students.get(parent(index)).compareTo(students.get(index)) < 0) {
         swap(parent(index), index);
         index = parent(index);
      }
   }

   private void maxHeapify(int index)
   {
      int left = left(index);
      int right = right(index);
      int largest = index;
      if (left <  size() && students.get(left).compareTo(students.get(largest)) > 0)
      {
         largest = left;
      }
      if (right <  size() && students.get(right).compareTo(students.get(largest)) > 0)
      {
         largest = right;
      }
      if (largest != index)
      {
         swap(index, largest);
         maxHeapify(largest);
      }  
   }   
}