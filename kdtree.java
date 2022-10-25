import java.io.*;
import java.io.FileNotFoundException;
import java.util.*;

public class kdtree {
    public TreeNode rootnode;
    public class Pair<A, B> {
        public A First;
        public B Second;
        public Pair() {
        }
        public Pair(A _first, B _second) {
            this.First = _first;
            this.Second = _second;
        }
        public A get_first() {

            return First;
        }
        public B get_second() {
            return Second;
        }
    }

    public class TreeNode {
        public TreeNode parent;
        public TreeNode left;
        public TreeNode right;
        public boolean isLeaf;
        public int numberleaves;
        public int xlimit;
        public int ylimit;
        public ArrayList<Pair<Integer, Integer>> list;
        public Pair<Integer, Integer> pt;
        public int depth;
    }


    public ArrayList<Pair<Integer, Integer>> XmergeSort(ArrayList<Pair<Integer, Integer>> initial) {
        ArrayList<Pair<Integer, Integer>> L = new ArrayList<Pair<Integer, Integer>>();
        ArrayList<Pair<Integer, Integer>> R = new ArrayList<Pair<Integer, Integer>>();
        int mid;
        if (initial.size() == 1) {
            return initial;
        }
        else {
            mid = initial.size() / 2;
            for (int i = 0; i < mid; i++) {
                L.add(initial.get(i));
            }
            for (int i = mid; i < initial.size(); i++) {
                R.add(initial.get(i));
            }
            L = XmergeSort(L);
            R = XmergeSort(R);
            xmerge(L, R, initial);
        }
        return initial;
    }

    private void xmerge(ArrayList<Pair<Integer, Integer>> left, ArrayList<Pair<Integer, Integer>> right, ArrayList<Pair<Integer, Integer>> initial) {
        int l = 0;
        int r = 0;
        int w = 0;
        while (l < left.size() && r < right.size()) {
            if (((left.get(l).First).compareTo(right.get(r).First)) < 0) {
                initial.set(w, left.get(l));
                l++;
            } else {
                initial.set(w, right.get(r));
                r++;
            }
            w++;
        }
        ArrayList<Pair<Integer, Integer>> out;
        int outIndex;
        if (l >= left.size()) {
            out = right;
            outIndex = r;
        } else {
            out = left;
            outIndex = l;
        }
        for (int i = outIndex; i < out.size(); i++) {
            initial.set(w, out.get(i));
            w++;
        }
    }
    public ArrayList<Pair<Integer, Integer>> YmergeSort(ArrayList<Pair<Integer, Integer>> initial) {
        ArrayList<Pair<Integer, Integer>> L = new ArrayList<Pair<Integer, Integer>>();
        ArrayList<Pair<Integer, Integer>> R = new ArrayList<Pair<Integer, Integer>>();
        int mid;
        if (initial.size() == 1) {
            return initial;
        }
        else {
            mid = initial.size() / 2;
            for (int i = 0; i < mid; i++) {
                L.add(initial.get(i));
            }
            for (int i = mid; i < initial.size(); i++) {
                R.add(initial.get(i));
            }
            L = XmergeSort(L);
            R = XmergeSort(R);
            xmerge(L, R, initial);
        }
        return initial;
    }

    private void ymerge(ArrayList<Pair<Integer, Integer>> left, ArrayList<Pair<Integer, Integer>> right, ArrayList<Pair<Integer, Integer>> initial) {
        int l = 0;
        int r = 0;
        int w = 0;
        while (l < left.size() && r < right.size()) {
            if (((left.get(l).Second).compareTo(right.get(r).Second)) < 0) {
                initial.set(w, left.get(l));
                l++;
            } else {
                initial.set(w, right.get(r));
                r++;
            }
            w++;
        }
        ArrayList<Pair<Integer, Integer>> out;
        int outIndex;
        if (l >= left.size()) {
            out = right;
            outIndex = r;
        } else {
            out = left;
            outIndex = l;
        }
        for (int i = outIndex; i < out.size(); i++) {
            initial.set(w, out.get(i));
            w++;
        }
    }

    public void res() {
        try {
            FileInputStream f = new FileInputStream("restaurants.txt");
            Scanner s = new Scanner(f);
            ArrayList<Pair<Integer, Integer>> xy = new ArrayList<Pair<Integer, Integer>>();
            String k = s.nextLine();
            while (s.hasNextLine()) {
                String[] parts = s.nextLine().split(",");
                String part1 = parts[0];
                String part2 = parts[1];
                Pair<Integer, Integer> p = new Pair<Integer, Integer>(Integer.parseInt(part1), Integer.parseInt(part2));
                xy.add(p);
            }
            TreeNode A = new TreeNode();
            A.depth = 0;
            A.numberleaves = xy.size();
            A.xlimit = XmergeSort(xy).get((A.numberleaves - 1) / 2).First;
            A.ylimit = Integer.MAX_VALUE;
            A.list = XmergeSort(xy);
            A.isLeaf = false;
            this.rootnode = A;
            g(this.rootnode);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
    }

    public void f(TreeNode A, ArrayList<Pair<Integer, Integer>> u) {
        if (A.depth % 2 == 0) {
            A.list = XmergeSort(u);
            A.numberleaves = A.list.size();
            A.xlimit = A.list.get(A.numberleaves / 2).First;
            A.ylimit = A.parent.ylimit;
        } else {
            A.list = YmergeSort(u);
            A.numberleaves = A.list.size();
            A.xlimit = A.parent.xlimit;
            A.ylimit = A.list.get(A.numberleaves / 2).Second;
        }
        A.isLeaf = false;
    }

    public void g(TreeNode B) {
        if (B.numberleaves > 2) {
            B.left=new TreeNode();
            B.right=new TreeNode();
            B.left.parent=B;
            B.right.parent=B;
            ArrayList<Pair<Integer, Integer>> S1= new ArrayList<Pair<Integer, Integer>>(B.list.subList(0, (B.numberleaves + 1) / 2));
            ArrayList<Pair<Integer, Integer>> S2=new ArrayList<Pair<Integer, Integer>>( B.list.subList((1 + B.numberleaves) / 2, B.numberleaves));
            B.left.depth = B.depth + 1;
            f(B.left, S1);
            B.right.depth = B.depth + 1;
            f(B.right, S2);
            g(B.left);
            g(B.right);
        }
        else {
            if(B.list.size()==2) {
                B.left = new TreeNode();
                B.right = new TreeNode();
                ArrayList<Pair<Integer, Integer>> SL1= new ArrayList<Pair<Integer, Integer>>(B.list.subList(0, (B.numberleaves + 1) / 2));
                ArrayList<Pair<Integer, Integer>> SL2=new ArrayList<Pair<Integer, Integer>>( B.list.subList((1 + B.numberleaves) / 2, B.numberleaves));
                B.left.parent = B;
                B.right.parent = B;
                B.left.pt = B.list.get(0);
                B.left.isLeaf = true;
                B.left.list=SL1;
                B.right.list=SL2;
                B.right.pt = B.list.get(1);
                B.right.isLeaf = true;
            }
            else{
                B.pt = B.list.get(0);
                B.isLeaf = true;
            }
        }
    }

    public int count(TreeNode A, int x1, int y1, int x2, int y2) {
        if (A.list.size()==1) {
            if(x1 <= A.pt.First && y1 <= A.pt.Second && x2 >= A.pt.First && y2 >= A.pt.Second) {
                return 1;
            }
            return 0;
        }

        else {
            return count(A.left, x1, y1, x2, y2) + count(A.right, x1, y1, x2, y2);
        }
    }
    public void Nearpt() {
        try {
            FileOutputStream fs = new FileOutputStream("output.txt",false);
            PrintStream p = new PrintStream(fs);
            Scanner s = new Scanner(new FileInputStream("queries.txt"));
            List<Pair<Integer, Integer>> que = new ArrayList<Pair<Integer, Integer>>();
            String k = s.nextLine();
            while (s.hasNextLine()) {
                String[] parts = s.nextLine().split(",");
                String part1 = parts[0];
                String part2 = parts[1];
                Pair<Integer, Integer> o = new Pair<Integer, Integer>(Integer.parseInt(part1), Integer.parseInt(part2));
                que.add(o);
            }
            for (int i = 0; i < que.size(); i++) {
                p.println(count(this.rootnode, que.get(i).First - 100, que.get(i).Second - 100, que.get(i).First + 100, que.get(i).Second + 100));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        kdtree K = new kdtree();
        K.res();
        K.Nearpt();
    }

}

