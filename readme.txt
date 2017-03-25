POX over HTTP 


Follow the below instructions to install the project in NETBEANS IDE :

1. Copy the POX-FoodMenu-lkusnoor.zip at any location on your local system

2. Importing Server : Go to IDE and click on File -> Open Project --> POX-FoodMenu-lkusnoor.zip -> POX-FoodMenu-lkusnoor

3. Importing Client : Go to IDE and click on File -> Open Project --> POX-FoodMenu-lkusnoor.zip -> POX-FoodMenuClient-lkusnoor

4. Once done, the Projects section should show both server and client .

6. The project has been successfully imported.
   
7. Now right click on the Project "POX-FoodMenu-lkusnoor" in the Project Explorer and click on build and then Run the Server 
   
8. The server will start running on the browser .
	
9. Now right click on the Project "POX-FoodMenuClient-lkusnoor" in the Project Explorer and click on build and then Run the Client 
	
10. Now you see applet running with two text boxes. Put the xml to be tested in the client Req textbox and click on Submit.

11. Response is received on right textbox i.e server Resp .

12. Once tested, go back on browser and resubmit new xml to be tested.



Test cases :

1. Format for adding FoodItem  :
	
		<NewFoodItems xmlns="http://cse564.asu.edu/PoxAssignment">
		<FoodItem country="GB"> 
			<name>Cornish Pasty</name>
			<description>Tender cubes of steak, potatoes and swede wrapped in flakey short crust pastry.  Seasoned with lots of pepper.  Served with mashed potatoes, peas and a side of gravy</description>
			<category>Dinner</category>
			<price>15.95</price>
		</FoodItem>
		</NewFoodItems >		
	
2. Format for Retriving FoodItem :

		<SelectedFoodItems xmlns="http://cse564.asu.edu/PoxAssignment">
			<FoodItemId>100</FoodItemId>
			<FoodItemId>156</FoodItemId>
		</SelectedFoodItems>
	
	
3. Adding invalid xml should throw invalid xml message :
	
		<SelectedFood xmlns=”http://cse564.asu.edu/PoxAssignment”>
			<FoodItemId>100</FoodItemId>
			<FoodItemId>156</FoodItemId>
		</SelectedFood>

	Response Message
		<InvalidMessage xmlns="http://cse564.asu.edu/PoxAssignment"/>
