package yuhui.huang

class Doc {
	String title
	String fileName
	Date dateCreated
	byte[] filedata
	
	String toString() {
		"${title}+${fileName}"
	}
	static brlogsTO = [users:User]
	
	static mapping = { filedata( type: 'materialized_blob' ) }

    static constraints = {
		title(blank:true)
		fileName()
		filedata()
		dateCreated()
    }
}
