package yuhui.huang

class User {
    String userName
    String password
    String fullName
	
    String toString() {
        "${fullName}"
    }
	
    static constraints = {
        fullName()      
        userName(unique: true)
        password(password: true)
        
    }
    static hasMany = [docs : Doc]
}