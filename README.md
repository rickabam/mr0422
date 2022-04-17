# mr0422
# Mike Rickabaugh - Tool Rental App
# Some notes on my design:
1) I opted to encapsulate the data associated with particular tool types (Daily Charge, Weekday Charge, Weekend Charge, and Holiday Charge) as Tool Policy objects. Each Tool object tool type has a corresponding Tool Policy object with a matching tool type. Tool Policies have a one-to-many relationship with Tools.
2) The Tool Policies of my Tool Store instance are kept in a hash map where the key is the tool type as a String. This is because each policy has a unique tool type. I opted to keep the tool type out of the policy and use the hash map to create the relationship.
3) The Tools of my Tool Store are kept in an array list inventory. Since the requirements indicate that the tool code should be a part of the object, I chose to enforce uniqueness with an extra check upon adding the Tool to Tool Store inventory and use a list instead of a map.
4) I make the assumption that holidays are mutually exclusive from weekends and weekdays and that the holiday status and holiday charge field will trump other considerations when calculating if the day is billable.
5) I made some minor changes to some of the variable names just to keep things less confusing to me. I used the original names from the requirements in print functions.
6) I have the unit tests set to leverage the print functions just to help test them. They run asynchronously though, so if you run the entire suite, the printed lines will not come out in order.
