package com.locate.driver.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/*
Implementation of K-dimensional tree to search efficiently
 */
public class LocationGraph implements Runnable {

    private final int LIMIT = 50000;
    private final int K = 3;
    private Node root = null;
    private final List<Driver> drivers;

    private static final Logger LOG = LoggerFactory.getLogger(LocationGraph.class);

    public LocationGraph(final List<Driver> drivers) {
        this.drivers = drivers;
    }

    @Override
    public void run() {
        // this method will get called from data holder after every 60 seconds.
        LOG.info("=========== purge and create tree process started ===========");

        //delete the previously created tree
        deleteTree(root);
        root = null;

        // create a K-D tree
        for (Driver driver : drivers) {
            insert(driver);
        }

        LOG.info("=========== purge and creation completed ===========");
    }


    public void deleteTree(Node root) {
        if (root == null)
            return;

        deleteTree(root.left);
        deleteTree(root.right);

        root = null;
    }

    public void insert(Driver driver) {
        root = insertRec(root, driver, 0);
    }

    public List<Driver> searchNearByDrivers(Customer customer){
        List<Driver> drivers = new ArrayList<>();
        return findNearestDrivers(root, customer, drivers, 0);
    }

    private List<Driver> findNearestDrivers(Node root, Customer customer, List<Driver> drivers, int depth) {

        if(root == null)
            return drivers;

        if(isThisDriverInRange(root.driver, customer)) {
            LOG.info("adding up a new driver in nearby list : " + root.driver);
            drivers.add(root.driver);
        }

        //find current dimension
        int cd = depth % K;

        if(root.driver.getPoint()[cd] < customer.getPoint()[cd])
            return findNearestDrivers(root.left, customer, drivers, depth + 1);
        return findNearestDrivers(root.right, customer, drivers, depth + 1);
    }

    private boolean isThisDriverInRange(final Driver driver, final Customer customer) {
        final double x = driver.getPoint()[0] - customer.getPoint()[0];
        final double y = driver.getPoint()[1] - customer.getPoint()[1];
        final double z = driver.getPoint()[2] - customer.getPoint()[2];
        if((x * x + y * y + z * z) <= customer.getLimit())
            return true;
        return false;
    }

    private Node insertRec(Node root, Driver driver, int depth) {

        if (root == null)
            return new Node(driver);

        //calculate current dimension
        int dimension = depth % K;

        //compare new driver with root based on current dimension
        if (driver.getPoint()[dimension] < root.driver.getPoint()[dimension])
            root.left = insertRec(root.left, driver, depth + 1);
        else
            root.right = insertRec(root.right, driver, depth + 1);

        return root;
    }


    private static class Node {
        Node left;
        Node right;
        Driver driver;

        Node(Driver driver) {
            this.driver = driver;
        }
    }
}
