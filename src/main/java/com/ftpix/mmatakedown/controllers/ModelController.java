package com.ftpix.mmatakedown.controllers;

import java.util.List;

public interface ModelController<E,F> {

	/**
	 * Get All from type E
	 * @return List<E>
	 * @throws Exception
	 */
	List<E> getAll() throws Exception;
	
	/**
	 * Get a single item via its id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	E get(F id) throws Exception;
	
	/**
	 * Insert a new item
	 * @param object
	 * @return
	 * @throws Exception
	 */
	E insert (E object) throws Exception;
	
	/**
	 * Update a single item
	 * @param object
	 * @return
	 * @throws Exception
	 */
	E update(E object) throws Exception;
	
	/**
	 * Delete a signe item
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean delete (F id) throws Exception;
}
