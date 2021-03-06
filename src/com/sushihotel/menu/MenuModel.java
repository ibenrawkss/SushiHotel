package com.sushihotel.menu;

import java.util.ArrayList ;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.sushihotel.database.DataStoreFactory;
import com.sushihotel.database.IDataStore;
import com.sushihotel.exception.DuplicateData;
import com.sushihotel.exception.EmptyDB;
import com.sushihotel.exception.InvalidEntity;


public class MenuModel {
	private static IDataStore dataStore = DataStoreFactory.getDataStore();
	
	private static final String EMPTY_DB_MSG = "Menu DB not found.";
	
	protected static boolean create(Meal meal) throws DuplicateData{
		List list;
		int size;
		Meal dbMeal;
		boolean idSet = false;
		
		list = (ArrayList)dataStore.read(IDataStore.DB_ENTITY_TYPE.MENU);
		
		size = list == null ? 0 : list.size();
		
		if(list == null)    {
            list = new ArrayList(); // declare array list without specific <obj> ref
        }    
		
// 		Checks Meal against dbMeal(reflects list) for existing meal based on mealID, mealName
        for(int i=0; i<list.size(); i++)   {
            dbMeal = (Meal)list.get(i);     
            if(dbMeal.getMealName().toLowerCase().equals(meal.getMealName().toLowerCase())) {
            	throw new DuplicateData(meal.getMealName(), Meal.MENU_SEARCH_TYPE.MEAL_NAME);
            }
            if (dbMeal.getMealID() != i+1 && idSet == false) {
            	meal.setMealID(i+1);
            	idSet = true;
            }
        }
        if (idSet == false) {
        	  meal.setMealID(size + 1);
        }
        
        list.add(meal);
        list.sort(Comparator.comparing(Meal::getMealID));
        return dataStore.write(list, IDataStore.DB_ENTITY_TYPE.MENU);
	}
        
    protected static Meal read(int mealID) throws EmptyDB, InvalidEntity {
    	List list;
    	Meal meal;
    	
    	list = (ArrayList)dataStore.read(IDataStore.DB_ENTITY_TYPE.MENU);
    	if(list == null)
            new EmptyDB(EMPTY_DB_MSG);
    	
        for (int i=0; i<list.size(); i++) {
        	meal = (Meal)list.get(i);
            if(meal.getMealID() == mealID)
                return meal;
        }
        throw new InvalidEntity(mealID + " not found.", Meal.MENU_SEARCH_TYPE.MEAL_ID);
    }
        
    protected static List<Meal> read() throws EmptyDB{ 
    	List list = null;
        List<Meal> newList = new ArrayList();
        Meal meal;

        list = (ArrayList)dataStore.read(IDataStore.DB_ENTITY_TYPE.MENU);
        
        if(list == null)
            throw new EmptyDB(EMPTY_DB_MSG);

        for(int i=0; i<list.size(); i++)    {
            meal = (Meal)list.get(i);
                newList.add(meal);
        }
        return newList;
    	
    }
    
	protected static boolean update(int mealID, Meal meal) throws EmptyDB, InvalidEntity{ 
		List list;
        Iterator iter; // iterator used to traverse through list
        Meal dbMeal;
        boolean trigger_flag = false;
		
        list = (ArrayList)dataStore.read(IDataStore.DB_ENTITY_TYPE.MENU); //assign Menu data from database into a List, (can use either arraylist/list)??
		
        if (list == null) { // empty list, throw exception EmptyDB
        	throw new EmptyDB(EMPTY_DB_MSG);
        }
        
        iter = list.iterator(); // returns an object of type iterator, points above the first MealName, must use .next();
        while(iter.hasNext())   { //check if there is next item in list
            dbMeal = (Meal)iter.next(); // assign next item
            if(dbMeal.getMealID() == mealID) { // remove if mealname is found
                dbMeal.setDesc(meal.getDesc());
                dbMeal.setMealName(meal.getMealName());
                dbMeal.setMealPrice(meal.getMealPrice());
                dbMeal.setPreparedMethod(meal.getPreparedMethod());
                trigger_flag = true;
                break;
            }
        }
        if(!trigger_flag) 
            throw new InvalidEntity(mealID + " not found. ", Meal.MENU_SEARCH_TYPE.MEAL_ID);
            
        meal.setMealID(mealID);
        
        return dataStore.write(list, IDataStore.DB_ENTITY_TYPE.MENU);
	}
	
	protected static boolean delete(int mealID) throws EmptyDB, InvalidEntity {
		List list;
		Iterator iter;
		Meal dbMeal;
		boolean trigger_flag = false;
		
		list = (ArrayList)dataStore.read(IDataStore.DB_ENTITY_TYPE.MENU);
		
		if(list == null) {
			throw new EmptyDB(EMPTY_DB_MSG);
		}
		
		iter = list.iterator();
		while(iter.hasNext()) {
			dbMeal = (Meal)iter.next();
			if (dbMeal.getMealID() == mealID) {
				iter.remove();
				trigger_flag = true;
				break;
			}
		}
		if (!trigger_flag) {
			throw new InvalidEntity(mealID + " not found.", Meal.MENU_SEARCH_TYPE.MEAL_ID);
		}
		
		return dataStore.write(list, IDataStore.DB_ENTITY_TYPE.MENU);
		
	}
}
