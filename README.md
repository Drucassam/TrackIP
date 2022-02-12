# TrackIP

Keep track of the IP addresses that are making the most requests.

## request_handled(ip_address)
This function adds the IP address to the IP list if it still not there or updates the requests count if the IP is already on the list. The function also calls “updateTopList” to update the top 100 list.

## updateTopList(ip_address, requests)
This function is responsible to maintain the top 100 IPs with the most requests.

## top100()
Returns the top 100 IP addresses by request count, with the highest traffic IP address first.

## clear()
Clears the IP lists.
