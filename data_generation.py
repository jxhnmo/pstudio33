from random import *
from csv import writer
from datetime import *
# from time import *

employees = [1,2,3,4,5]
ingredients = ["buns","lettuce","patty","cheese","bacon","bread","tomato","crispy chicken","spicy crispy chicken","grilled chicken","croutons","fries"]
menu_items = {
    "bacon cheeseburger":["buns","patty","cheese","bacon"],
    "cheeseburger":["buns","patty","cheese"],
    "patty melt":["bread","patty","cheese"],
    "hamburger":["buns","patty","lettuce","tomato"],
    "aggie chicken club":["buns","crispy chicken","lettuce","bacon"],
    "revs grilled chicken sandwich":["buns","grilled chicken","lettuce","tomato"],
    "spicy chicken sandwich":["buns","spicy crispy chicken","lettuce"],
    "chicken caesar salad":["grilled chicken","lettuce","lettuce","lettuce","croutons"],
    "french fries":["fries"]
}
menu_item_costs = {
    "bacon cheeseburger":8.65,
    "cheeseburger":7.45,
    "patty melt":5.45,
    "hamburger":8.45,
    "aggie chicken club":6.75,
    "revs grilled chicken sandwich":6.60,
    "spicy chicken sandwich":6.30,
    "chicken caesar salad":6.80,
    "french fries":3.50
}

menu_file = open("menu_items.csv","w")
menu_csv = writer(menu_file)
menu_id = 1
menu_csv.writerow(["id","name","available"])
menu_item_ids = {}
for x in menu_items:
    menu_csv.writerow([menu_id,x,True])
    menu_item_ids[x] = menu_id
    menu_id += 1
menu_file.close()

item_file = open("inventory_items.csv","w")
item_csv = writer(item_file)
item_id = 1
item_ids = {}
item_csv.writerow(["id","item_name","stock"])
for x in ingredients:
    item_csv.writerow([item_id,x,0])
    item_ids[x] = item_id
    item_id += 1
item_file.close()

ingredients_file = open("ingredients.csv","w")
ingr_csv = writer(ingredients_file)
ingr_id = 1
ingr_csv.writerow(["id","item_id","menu_id","num"])
for x in menu_items:
    ing_count = {}
    for ing in menu_items[x]:
        if ing in ing_count:
            ing_count[ing] += 1
        else:
            ing_count[ing] = 1
    for ing in ing_count:
        ingr_csv.writerow([ingr_id,item_ids[ing],menu_item_ids[x],ing_count[ing]])
        ingr_id += 1



available_times = []
curr_time = datetime(year=2023,month=1,day=1,hour=10,minute=30,second=0)
lunch_end = datetime(year=2023,month=1,day=1,hour=13,minute=30,second=0)
dinner_begin = datetime(year=2023,month=1,day=1,hour=18,minute=30,second=0)
dinner_end = datetime(year=2023,month=1,day=1,hour=20,minute=30,second=0)
closing_time = datetime(year=2023,month=1,day=1,hour=23,minute=0,second=0)
inc = timedelta(seconds=1)

while curr_time < closing_time:
    available_times.append(curr_time.time())
    if curr_time < lunch_end:
        curr_time = curr_time + inc
    elif curr_time < dinner_begin:
        curr_time = curr_time + inc*5
    elif curr_time < dinner_end:
        curr_time = curr_time + inc
    else:
        curr_time = curr_time + inc*3

ingredients_file.close()
day1 = date(2023,1,1)
inc = timedelta(days=1)

multipliers = [1.3,1.7,2.4,3.5,4.2,2.6,1]
num_items = [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,3,3,3,4]
avail_emp = [1,2,2,2,3,3,3,4,4,4,5,5]

sales_file = open("sales_transactions.csv","w")
sales_csv = writer(sales_file)
sales_id = 1
sales_csv.writerow(["id","cost","employee_id","purchase_time"])

sale_item_file = open("sales_items.csv","w")
sale_item_csv = writer(sale_item_file)
sale_item_id = 1
sale_item_csv.writerow(["id","sales_id","menu_id"])


for i in range(randrange(366,410)):
    day = day1 + (inc)*i
    multi = multipliers[day.weekday()]
    num_orders = randrange(int(150*multi),int(250*multi))
    times = sample(available_times,num_orders)
    for t in times:
        emp_id = choice(avail_emp)
        num = choice(num_items)
        cost = 0
        for j in range(num):
            item = choice(list(menu_items.keys()))
            cost += menu_item_costs[item]
            sale_item_csv.writerow([sale_item_id,sales_id,menu_item_ids[item]])
            sale_item_id += 1
        sales_csv.writerow([sales_id,cost,emp_id,t.strftime("%H:%M:%S")])
        sales_id += 1

sales_file.close()
sale_item_file.close()