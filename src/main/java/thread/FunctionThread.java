package thread;

import java.util.ArrayList;

public class FunctionThread
{
  private final ArrayList<String> functionLabels;

  public FunctionThread(ArrayList<String> functionLabels)
  {
    this.functionLabels = functionLabels;
  }

  public ArrayList getArrayList()
  {
    return this.functionLabels;
  }
}