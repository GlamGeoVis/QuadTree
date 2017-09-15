package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import datastructure.HierarchicalClustering;
import datastructure.QuadTree;
import datastructure.QuadTree.InsertedWhen;
import datastructure.Square;

public class PointIO {

    public static void read(File file, QuadTree tree) {
        try (Scanner reader = new Scanner(new FileInputStream(file))) {
            while (reader.hasNextDouble()) { 
            	System.out.println("Read input file");
            	tree.insertCenterOf(new Square(reader.nextDouble(),
                        reader.nextDouble(), reader.nextInt(10),
                        reader.next())
                        );// read next string value for book genre
            }
            System.out.println("This is the tree: "+ tree);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void write(QuadTree tree, File file) {
        try (PrintStream writer = new PrintStream(new FileOutputStream(file))) {
            for (QuadTree leaf : tree.leaves()) {
                for (Square s : leaf.getSquares(InsertedWhen.BY_ALGORITHM)) {
                    writer.println(s.getX() + " " + s.getY() + " " + s.getN());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

	public static void printClustering(HierarchicalClustering result) {
		double time = 1.0;
		System.out.println("Print clustering...");

    	_doPrinting(result, time);
    	
    	System.out.println("==========================");
    	System.out.println(result);
	}

	private static void _doPrinting(HierarchicalClustering result, double time) {
		double at = result.getAt();
		// System.out.println(at + "...");
		if(at < time) {
			System.out.println(at + ": " + result.getSquare());
		}
		Set<HierarchicalClustering> createdFroms = result.getCreatedFrom();
		if(createdFroms!=null) {
			for(HierarchicalClustering from : createdFroms) {
				_doPrinting(from, time);
			}
		}
		/*if(result.getMergedInto()!=null) {
			_doPrinting(result.getMergedInto(), time);
		}*/
	}

}
