package messages;

import gameplay.Company;

/**
 * 
 * @author alancoon
 * Updated Friday (11/18) 11:39 PM changed to Company type for input
 * parameter from String.
 */
public class BeginAuctionBidMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Company company;
	private String companyname;
	
	public BeginAuctionBidMessage(Company company, String companyname) {
		super(MessageType.beginBid);
		this.company = company;
		this.companyname = companyname;
	} 
	
	public Company getCompany() {
		return company;
	}
	
	public String getCompanyName() {
		return companyname;
	}
}
