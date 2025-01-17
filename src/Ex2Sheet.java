import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.nio.file.Files;
import java.nio.file.Paths;




public class Ex2Sheet implements Sheet {
  private Cell[][] table;
  private String fileName;

  public Ex2Sheet(int x, int y) {
    table = new SCell[x][y];
    for (int i = 0; i < x; i = i + 1) {
      for (int j = 0; j < y; j = j + 1) {
        table[i][j] = new SCell("");
      }
    }
    eval();
  }

  public Ex2Sheet() {
    this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
  }

  @Override
  public String value(int x, int y) {
    return eval(x, y);
  }


  @Override
  public Cell get(int x, int y) {
    return table[x][y];
  }

  @Override
  public Cell get(String posses) {
    Position position = parsePosition(posses);

    return table[position.x][position.y];
  }

  @Override
  public int width() {
    return table.length;
  }

  @Override
  public int height() {
    return table[0].length;
  }

  @Override
  public void set(int x, int y, String s) {
    Cell c = new SCell(s);
    table[x][y] = c;
  }

  @Override
  public void eval() {
  }

  @Override
  public boolean isIn(int x, int y) {
    return x >= 0 && x < width() && y >= 0 && y < height();
  }

  @Override
  public int[][] depth() {
    int[][] ans = new int[width()][height()];
    for (int x = 0; x < width(); x++) {
      for (int y = 0; y < height(); y++) {
        ans[x][y] = depthHepler(List.of(new Position(x, y)));
        table[x][y].setOrder(ans[x][y]);
      }
    }

    return ans;
  }

  int depthHepler(List<Position> positions) {
    Position last = positions.getLast();
    Cell cell = table[last.x][last.y];
    String line = cell.getData();

    boolean pos = false;


    ArrayList<String> parts = new ArrayList<>();

    for (int i = 0; i < line.length(); i++) {
      char ch = line.charAt(i);
      if (!pos) {
        if (Character.isAlphabetic(ch)) {
          pos = true;
          parts.add(ch + "");
        }
      } else {
        if (Character.isDigit(ch))
          parts.set(parts.size() - 1, parts.getLast() + ch);
        else
          pos = false;
      }
    }

    ArrayList<Position> positionNew = new ArrayList<Position>();
    for (int i = 0; i < parts.size(); i++) {
      Position newCoord = parsePosition(parts.get(i));

      if (isPositions(positions, newCoord))
        return -1;

      positionNew.add(newCoord);
    }

    // for (int i = 0;)

    return 0;
  }

  @Override
  public void load(String fileName) throws IOException {
    List<String> strs = Files.readAllLines(Paths.get(fileName));

    for (int x = 0; x < width(); x++) {
      for (int y = 0; y < height(); y++) {
        table[x][y].setData("");
      }
    }

    for (int i = 1; i < strs.size(); i++) {
      String[] split = strs.get(i).split(",");
      int x = Integer.parseInt(split[0]);
      int y = Integer.parseInt(split[1]);

      table[x][y].setData(split[2]);
    }
  }

  @Override
  public void save(String fileName) throws IOException {
    String first_line = "I2CS ArielU: SpreadSheet (Ex2) assignment";

    for (int y = 0; y < height(); y++) {
      for (int x = 0; x < width(); x++) {
        SCell cell = (SCell)table[x][y];
        String line = cell.getData();
        if (line != "") {
          first_line += x + "," + y + "," + line + "\n";
        }
      }
    }

    FileWriter myWriter = new FileWriter(fileName);
    myWriter.write(first_line);
    myWriter.close();
  }


  @Override
  public String eval(int x, int y) {
    Cell cell = table[x][y];
    String line = cell.getData();

    String computable = compute(line, List.of(new Position(x, y)));

    if (Objects.equals(computable, Ex2Utils.ERR_FORM)) cell.setType(Ex2Utils.ERR_FORM_FORMAT);
    else if (Objects.equals(computable, Ex2Utils.ERR_CYCLE)) cell.setType(Ex2Utils.ERR_CYCLE_FORM);
    else if (parseDouble(computable) != -1 && !line.startsWith("=")) cell.setType(Ex2Utils.NUMBER);
    else if (parseDouble(computable) != -1) cell.setType(Ex2Utils.FORM);
    else cell.setType(Ex2Utils.TEXT);

    return computable;
  }

   String compute(String line, List<Position> positions) {
    if (line.startsWith("=")) {
      String str = line.substring(1).replaceAll(" ", "");

      return helperCompute  (str, positions);
    }

    double num = parseDouble(line);
    if (num != -1) {
      return String.valueOf(num);
    }

    return line;
  }

  String helperCompute(String form, List<Position> positions) {
    while (form.startsWith("(") && form.endsWith(")"))
      form = form.substring(1, form.length() - 1);

    int signIndex = getOpIndex(form);

    if (signIndex == -1) {
      double result = parseDouble(form);
      if (result != -1) {
        return String.valueOf(result);
      }

      Position position = parsePosition(form);
      if (position.x != -1) {
        if (isPositions(positions, position))
          return Ex2Utils.ERR_CYCLE;

        if (!isIn(position.x, position.y))
          return Ex2Utils.ERR_FORM;

        Cell cell = table[position.x][position.y];
        String cellExpr = cell.getData();

        List<Position> positionsCopy = new ArrayList<Position>(positions);
        positionsCopy.add(position);

        return compute(cellExpr, positionsCopy);
      }

      return Ex2Utils.ERR_FORM;
    }

    String sub0 = form.substring(0, signIndex);
    String sub1 = form.substring(signIndex + 1);
    char op = form.charAt(signIndex);

    if (sub0 != "") {
      String num0Str = helperCompute(sub0, positions);
      String num1Str = helperCompute(sub1, positions);

      if (Objects.equals(num0Str, Ex2Utils.ERR_FORM) || Objects.equals(num1Str, Ex2Utils.ERR_FORM))
        return Ex2Utils.ERR_FORM;

      if (Objects.equals(num0Str, Ex2Utils.ERR_CYCLE) || Objects.equals(num1Str, Ex2Utils.ERR_CYCLE))
        return Ex2Utils.ERR_CYCLE;

      double num0 = Double.parseDouble(num0Str);
      double num1 = Double.parseDouble(num1Str);

      double result = AllOperators(num0, num1, op);
      return String.valueOf(result);
    }

    if (op == '+' || op == '-') {
      String num1Str = helperCompute(sub1, positions);

      if (parseDouble(num1Str) == -1)
        return num1Str;

      double num1 = parseDouble(num1Str);

      double result = PlusMinus(num1, op);
      return String.valueOf(result);
    }

    return Ex2Utils.ERR_FORM;
  }

  int getOpIndex(String sttr) {
    boolean found = false;

    int db = Integer.MAX_VALUE;
    int ob = Integer.MAX_VALUE;
    int ib = Integer.MAX_VALUE;

    int dc = 0;
    for (int ic = 0; ic < sttr.length(); ic++) {
      char ch = sttr.charAt(ic);

      if (ch == '(') {
        dc++;
        continue;
      }
      if (ch == ')') {
        dc--;
        continue;
      }

      int oc = signType(ch);
      if (oc == -1)
        continue;

      if (isOp(ch) && isOpBetter(db, ob, ib, dc, oc, ic)) {
        found = true;
        db = dc;
        ob = oc;
        ib = ic;
      }
    }

    if (!found)
      return -1;

    return ib;

  }

  boolean isOp(char ch) {
    return ch == '+' || ch == '-' || ch == '*' || ch == '/';
  }

  boolean isOpBetter(int DD, int OD, int OB, int DC, int CC, int BD) {
    return DD > DC || DD == DC && (OD > CC || OD == CC && OB > BD);
  }

  int signType(char op) {
    return switch (op) {
      case '+' -> 0;
      case '-' -> 0;
      case '*' -> 1;
      case '/' -> 1;
      default -> -1;
    };
  }

  Position parsePosition(String s) {
    if (s.isEmpty())
      return new Position(-1, -1);

    char xChr = s.charAt(0);

    int x = Character.isUpperCase(xChr) ? xChr - 'A'
        : Character.isLowerCase(xChr) ? xChr - 'a'
            : -1;
    if (x == -1)
      return new Position(-1, -1);

    int y = parseInt(s.substring(1));
    if (y == -1)
      return new Position(-1, -1);

    return new Position(x, y);
  }

  double PlusMinus(double n, char op) {
    return switch (op) {
      case '+' -> n;
      case '-' -> -n;
      default -> Integer.MIN_VALUE;
    };
  }

  double AllOperators(double n0, double n1, char op) {
    return switch (op) {
      case '+' -> n0 + n1;
      case '-' -> n0 - n1;
      case '*' -> n0 * n1;
      case '/' -> n0 / n1;
      default -> Integer.MIN_VALUE;
    };
  }

  double parseDouble(String s) {
    try {
      return Double.parseDouble(s);
    } catch (Exception e) {
      return -1;
    }
  }

  int parseInt(String s) {
    try {
      return Integer.parseInt(s);
    } catch (Exception e) {
      return -1;

    }
  }

  boolean isPositions(List<Position> positions, Position pos) {
    for (int i = 0; i < positions.size(); i++) {
      Position coordCrnt = positions.get(i);
      if (pos.x == coordCrnt.x && pos.y == coordCrnt.y)
        return true;
    }

    return false;
  }
}
