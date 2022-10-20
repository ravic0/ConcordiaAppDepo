
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;//you need to use this import when completing the excercise
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;//you need to use this import when completing the excercise

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;//you need to use this import when completing the excercise



public class ReconciliationServiceTest {
	
	ReconciliationService service;
	
	@Mock FinancialTransactionDAO financialTransactionDAO;
	@Mock MembershipDAO membershipDAO;
	@Mock PayPalFacade payPalFacade;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new ReconciliationService(financialTransactionDAO,
				membershipDAO, payPalFacade);
		MembershipStatusDto basicMembership =
				new MembershipStatusDto();
		basicMembership.setDeductible(.3);
		//TODO: mock membershipDAO.getStatusFor(any id) to return the basic membership
		when(membershipDAO.getStatusFor(Mockito.anyString())).thenReturn(basicMembership);//this is not mandatory
	}
	
		
	@Test
	//This test work should pass
	public void when_no_Transaction_to_process_Service_RETURNS_Processing_Count_Zero() throws Exception{
		assertEquals(0, service.reconcile());
	}
	
	
	@Test
	public void when_Transaction_to_process_Service_RETURNS_Processing_Count_One() throws Exception {
		//TODO: 1. We will concentrate on when DAO returns 1 transaction.
		// Stub the retrieval method to return a transaction:
		assertEquals(1, service.reconcile());
	}

	@Test
	public void when_transactions_exists_all_of_them_are_retrieved() throws Exception {
		
		/*TODO 2.
		 * Add a test to verify that the reconcile method calls membershipDAO to
		 * fetch the membership details for a developer. In the test, create a list of
		 * transactions and add only one transaction with the developer's ID as USER100 and USER101.
		 * Stub financialTransactioDAO to retrieve this list.
		 */
				
		 assertEquals(1, service.reconcile());
		 /* During test execution, the service will get this list and then it should ask
		 * membershipDAO to get the details of developer USER100 and USER101. We will verify that in
		 * the test using Mockito's verify() API. Add the verify code below.
		 */		
	}
	
	@Test
	public void when_transactions_exist_then_membership_details_is_retrieved_for_each_developer() throws Exception {
		/*
		 * TODO: 3. Now add a test to verify transactions, but this time for three of them.
		 * This is a very important stage in TDD. Once we are done with one, we should test our code against
		 * many. In a test we will create three transactions: one for John, Jim and another one
		 * for Bob. We will expect that for all developers membershipDAO will be
		 * called. We are going to use ArgumentCaptor and Times to verify the
		 * invocation. Verification will check the number of invocations by passing new
		 * Times(3), then the argument captor will capture arguments for all
		 * invocations. Finally, we will ask the argument captor to return the list of
		 * invocations and from that list we will verify whether membershipDAO was
		 * invoked for both Bob and John:
		 */
		
		//add the mock code to retrieve the unsettled transactions using multipleTxs
		
		assertEquals(3, service.reconcile());
		
		//Declare an argument captor below
		
		//Verify the number of invocations by passing new Times(3) below.
		
		//Use the argCaptor to return the list of invocations below
		
//		From that list verify whether membershipDAO was invoked for Bob and John below

		/*
		 * assertEquals(johnsDeveloperId, passedValues.get(0));
		 * assertEquals(bobsDeveloperId, passedValues.get(1));
		 * assertEquals(jimsDeveloperId, passedValues.get(2));
		 * //uncomment when you fill the code above
		 */
	}
	
	@Test
	public void when_transaction_exists_Then_verify_payable_invocation() throws Exception {
		/* TODO: 4. Verify that the pay advice was sent. Create a transactional list for
		 * Bob, a developer. The createTxDto(...) method creates a Transaction Dto
		 * instance from the developer ID, PayPal ID, and app price
		 */
		List<TransactionDto> bobsTransactionList = new ArrayList<TransactionDto>();
		String bobsDeveloperId = "BOB999";
		String bobsPayPalId = "bob@paypal.com";
		double bobsAppPrice = 100.00;
		bobsTransactionList.add(createTxDto(bobsDeveloperId, bobsPayPalId, bobsAppPrice));
		// mock the unsettled bob's transaction below.
		
		assertEquals(1, service.reconcile());
		//verify the call to the payPalFacade.sendAdvice method
		
	}

	// Don't change this class
	private TransactionDto createTxDto(String developerId, String payPalId,
			double appPrice) {
		/*
		 * The createTxDto(...) method creates a Transaction Dto instance from the
		 * developer ID, PayPal ID, and app price.
		 */
		TransactionDto transdto = new TransactionDto();
		transdto.setTargetId(developerId);
		transdto.setTargetPayPalId(payPalId);
		transdto.setAmount(appPrice);
		return transdto;
	}
	
	@Test
	public	void calculates_payable() throws Exception {
		/*
		 * TODO: 5. We need to calculate the amount payable. How do we test this? Stub
		 * MemebershipDAO to return a Premium membership dto object. This means 8 percent
		 * is deducted from the original app price. If the app price is USD 1000.00,
		 * then PayPal payment advice should be USD 920.00. You will need to override the
		 * membershipDto created in the setUp method above and create a new premium membership with deductible as 0.08
		 *  Use Mockito's ArgumentCaptor
		 * method to verify that.
		 */
		List<TransactionDto> ronaldosTransactions = new ArrayList<TransactionDto>();
		String ronaldosDeveloperId = "ronaldo007";
		String ronaldosPayPalId = "Ronaldo@RealMadrid.com";
		double ronaldosSoccerFee = 1000.00;
		ronaldosTransactions.add(createTxDto(ronaldosDeveloperId, ronaldosPayPalId, ronaldosSoccerFee));
		//mock mock the unsettled Ronaldo's transaction below.
		
		//instantiate an ArgumentCaptor for calculateAdvice below

		//verify that a call to payPalFacade.sendAdvice was sent from the argumentCaptor
		
		//corroborate that calculateAdvice.getValue().getAmount == 920
		//assertTrue(920.00 == calculatedAdvice.getValue().getAmount());//uncomment when you fill the code above
	}
	
	
	@Test
	public void calculates_payable_with_multiple_Transaction() throws Exception {
		/*
		 * TODO: 6. Now it's time to test multiple transactions—one with CAD 200.00 
		 * and another with CAD 15.00, and Standard and Premium memberships. 
		 * The deductible is 30 percent and 10 percent respectively. 
		 * memberShip(double percent) method creates membershipStatusDto. 
		 * Stub the membershipDAO instance to return membership deductible 
		 * 10 percent for John and 30 percent for Dave. Use ArgumentCaptor to 
		 * capture the PayPalFacade call. Then, verify 
		 * that the correct deductible was computed and passed to PayPal  
		 * facade for both the developers
		 */

		List<TransactionDto> transactionList = new ArrayList<TransactionDto>();
		String johnsDeveloperId = "john001";
		String johnsPayPalId = "john@gmail.com";
		double johnsAppFee = 200;
		transactionList.add(createTxDto(johnsDeveloperId, johnsPayPalId, johnsAppFee));
		String davesDeveloperId = "dave888";
		String davesPayPalId = "IamDave009@yahoo.co.uk";
		int davesAppFee = 15;
		transactionList.add(createTxDto(davesDeveloperId, davesPayPalId, davesAppFee));
		// mock financialTransactionDAO to retrieve the unsettled transactions from transactionList

		
		//Use the memberShip(double percent) method to create an instance of membershipStatusDto and
		//set the corresponding deductible.
		//Note. When we use argument matchers, then all the arguments should use matchers. If we want to
		//use a specific value for an argument, then we can use eq() method.
		// mock the membership for johnDeveloperId to return .10
		
		// mock the membership for davesDeveloperId to return .30
		
		assertEquals(2, service.reconcile());
		// Instantiate an ArgumentCaptor<PaymentAdviceDto> for calculatedAdvice
		
		//verify the call to the method payPalFacade Times(2)

		//corroborate that the correct deductible was computed and passed to facade for both the developers:
		/*
		 * assertTrue(180.00 == calculatedAdvice.getAllValues().get(0).getAmount());
		 * assertTrue(10.5 == calculatedAdvice.getAllValues().get(1).getAmount());
		 * //uncomment when you fill the code above
		 */
	}

	// Do not change this code
	private MembershipStatusDto memberShip(double d) {
		//The memberShip(double percent) method creates membershipStatusDto
		MembershipStatusDto msdto = new MembershipStatusDto();
		msdto.setDeductible(d);
		return msdto;
	}
	
	@Test
	public void calculates_payable_with_multiple_Transaction_For_same_developer() throws Exception {
		/*
		 * TODO: 7. One thing is still missing. How can we minimize the PayPal
		 * Transactions? If a developer develops two apps, we should invoke PayPal
		 * facade only once not twice. PayPal charges against each transaction and also
		 * multiple transaction calls can create performance issues. Add a test for the
		 * developer Janet, who has two apps: FishPond and TicTacToe. Default
		 * membership is Basic, with 30 percent deductible.
		 */
		List<TransactionDto> janetsAppFees = new ArrayList<TransactionDto>();
		String janetsDeveloperId = "janet12567";
		String janetsPayPalId = "JanetTheJUnitGuru@gmail.com";
		double fishPondAppFee = 200;
		double ticTacToeAppFee = 100;
		janetsAppFees.add(createTxDto(janetsDeveloperId, janetsPayPalId, fishPondAppFee));
		janetsAppFees.add(createTxDto(janetsDeveloperId, janetsPayPalId, ticTacToeAppFee));
		//// mock financialTransactionDAO to retrieve the unsettled transactions from janetsappFees
		
		assertEquals(2, service.reconcile());
		// Instantiate an ArgumentCaptor<PaymentAdviceDto> for calculatedAdvice
		
		//verify the call to the method payPalFacade Times(1)for janet:

		//corroborate that the correct advice was calculated 
		//assertTrue(210.00 == calculatedAdvice.getValue().getAmount()); //uncomment when you fill the code above
	}
}
