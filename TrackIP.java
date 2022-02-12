package main;

import java.util.*;

public class TrackIP {

    //stores all the ip addresses
    private HashMap<String, Integer> ipList = new HashMap<>();
    //stores the ips with the most requests
    private List<Map.Entry<String, Integer>> topList = new java.util.LinkedList<>();
    //defines the size limit for topList
    final int TOP_IPS = 100;


    /**
     * Saves number of request per ip_address and updates the list of ips with the most requests
     * @param ip_address IP address
     */
    public void request_handled(String ip_address) {
        //number of requests
        int requests = 1;

        //adding or updating number of requests for the ip address
        if (ipList.containsKey(ip_address)) {
            requests = ipList.get(ip_address);
            requests++;
            ipList.put(ip_address, requests);
        } else {
            ipList.put(ip_address, requests);
        }

        //Updates the topList
        updateTopList(ip_address, requests);

    }

    /**
     * Returns the top 100 ips
     * @return top 100 ips
     */
    public List<Map.Entry<String, Integer>> top100() {
        return topList;
    }

    /**
     * Clears ip lists
     */
    public void clear() {
        ipList = new HashMap<String, Integer>();
        topList = new java.util.LinkedList<>();
    }

    /**
     * This function updates the list with ip addresses with the most requests
     * @param ip_address IP address
     * @param requests number of requests
     */
    private void updateTopList(String ip_address, int requests){

        //if it's not the first item in the list
        if (topList.size() > 0) {
            //add ip to the topList if topList did not reach max size
            // or if the ip made more requests than the ip on the bottom of the list
            if (topList.size() < TOP_IPS || requests > topList.get(topList.size() - 1).getValue()) {
                //variables used to save how far we went on when looping the topList
                int index = 0, positionToIns = topList.size() - 1;
                //flags to help control when to stop looping topList
                boolean foundIp = false, foundPos = false;

                //find the current position of the ip in the toplist
                // or if its not in the topList yet, find the position where its going to be added
                while (index < topList.size() && !foundIp) {
                    if (topList.get(index).getKey().equals(ip_address)) {
                        foundIp = true;
                    } else {
                        if (topList.get(index).getValue() < requests && !foundPos) {
                            positionToIns = index - 1;
                            foundPos = true;
                        }
                        index++;
                    }
                }
                //if index = 0 it means ip_address is in the top of topList
                if (index > 0) {
                    //set start point so we don't have to loop through topList all over again
                    if (!foundIp) {
                        index = positionToIns;
                    } else {
                        topList.remove(index);
                        index--;
                    }
                    //when ip is added to the list the iteration through topList can stop
                    boolean added = false;

                    do {
                        //find the right position to add the ip
                        if (requests <= topList.get(index).getValue()) {
                            topList.add(index + 1, new AbstractMap.SimpleEntry(ip_address, requests));
                            added = true;
                        }
                        index--;
                    } while (!added && index >= 0);
                    //if never added it means it is the top of the list
                    if (!added)
                        topList.add(0, new AbstractMap.SimpleEntry(ip_address, requests));

                    //ip_address was in the top of the list, just update the requests
                } else {
                    topList.set(0, new AbstractMap.SimpleEntry(ip_address, requests));
                }
            }
        } else //first item of the list, just add it to the top
            topList.add(0, new AbstractMap.SimpleEntry(ip_address, requests));

        //remove the last item if topList size is bigger than TOP_IPS
        if (topList.size() > TOP_IPS)
            topList.remove(topList.size() - 1);

    }

}
