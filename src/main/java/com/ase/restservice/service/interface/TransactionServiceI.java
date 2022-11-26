package com.ase.restservice.service;

import com.ase.restservice.exception.AccountNotFoundException;
import com.ase.restservice.exception.InvalidOrderTypeException;
import com.ase.restservice.exception.InvalidTransactionException;
import com.ase.restservice.exception.ResourceNotFoundException;
import com.ase.restservice.model.Asset;
import com.ase.restservice.model.Stock;
import com.ase.restservice.model.Transaction;
import java.util.Optional;

/**
 * Interface for Transaction service.
 */
public interface TransactionServiceI {
  /**
   * Write a new transaction to the database.
   * @param transaction new Transaction
   * @return returns the asset that was created/affected by this transaction
   * @throws Exception if user does not exist
   */
  Optional<Asset> createTransaction(Transaction transaction)
      throws AccountNotFoundException, ResourceNotFoundException,
      InvalidOrderTypeException, InvalidTransactionException;

  /**
   * Update a transaction status in the database.
   * @param transaction transaction to update
   * @param status new status
   */
  void updateTransactionStatus(Transaction transaction, String status);

  /**
   * Executes transactions to buy/sell assets. Directs to helper methods based on transaction type.
   *
   * @param transaction Transaction object placed
   * @return return the updated asset unless the asset was deleted (in the case the user sold
   *        all the shares of the asset), then return null.
   * @throws AccountNotFoundException if account is not found in database
   * @throws ResourceNotFoundException if user does not have the asset
   * @throws InvalidOrderTypeException when transaction type is not buy or sell
   */
  Optional<Asset> executeTransaction(Transaction transaction)
    throws AccountNotFoundException, ResourceNotFoundException,
    InvalidOrderTypeException, InvalidTransactionException;

  /**
   * Executes buy transactions by doing the following: Updating/creating account asset,
   * updating account balance, updating transaction status.
   *
   * @param transaction Transaction object to be executed, with transactionType="BUY"
   * @param stock Stock to be bought
   * @return account's updated asset after the buyTransaction has been executed
   * @throws AccountNotFoundException if account does not exist in the database
   */
  Asset buyTransaction(Transaction transaction, Stock stock) throws AccountNotFoundException;

  /**
   * Executes sell transaction by doing the following: Updating/deleting account asset,
   * updating account balance, updating transaction status.
   *
   * @param transaction Transaction object to be executed, with transactionType="SELL"
   * @param stock Stock to be sold
   * @return account's updated asset after sellTransaction has been excecuted, return null in
   *        the case that all the asset has been sold (asset has been deleted)
   * @throws AccountNotFoundException if account does not exist in the database
   * @throws InvalidTransactionException if transaction type is not buy or sell
   * @throws ResourceNotFoundException if user does not have sufficient assets
   */
  Optional<Asset> sellTransaction(Transaction transaction, Stock stock)
    throws AccountNotFoundException, InvalidTransactionException, ResourceNotFoundException;
}
