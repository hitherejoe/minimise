import Foundation
import SharedAuthentication
import Backend
import Firebase

class AuthenticationViewModel: ObservableObject, AuthenticateView {
    
    @Published internal var state: AuthenticationState = AuthenticationState.Idle(userEmail: "", userPassword: "", mode: AuthenticateMode.SignUp.init())
    private var backendService: BackendProvider
    
    init(backendService: BackendProvider) {
        self.backendService = backendService
    }
    
    func setEmailAddress(emailAddress: String) {
        self.state = self.state.build { (builder) in
            builder.userEmail = emailAddress
        }
    }
    
    func setPassword(password: String) {
        self.state = self.state.build { (builder) in
            builder.userPassword = password
        }
    }
    
    func toggleAuthenticationMode() {
        if (state.authenticationMode.isKind(of: AuthenticateMode.SignIn.self)) {
            self.state = self.state.build { (builder) in
                builder.mode = AuthenticateMode.SignUp.init()
            }
        } else {
            self.state = self.state.build { (builder) in
                builder.mode = AuthenticateMode.SignIn.init()
            }
        }
    }
    
    func authenticate() {
        if (state.authenticationMode.isKind(of: AuthenticateMode.SignUp.self)) {
            signUp()
        } else {
            signIn()
        }
    }
    
    func signIn() {
        self.state = self.state.build { (builder) in
            builder.state = AuthenticationState.Loading.init(userEmail: "", userPassword: "", mode: self.state.authenticationMode)
        }
        /*
        Auth.auth().signIn(withEmail: self.state.emailAddress, password: self.state.password) { authResult, error in
            if (authResult?.credential != nil) {
                self.state = self.state.build { (builder) in
                    builder.state = AuthenticationState.Success.init()
                }
            } else {
                self.state = self.state.build { (builder) in
                    builder.state = AuthenticationState.Error.init(userEmail: "", userPassword: "", mode: self.state.authenticationMode, message: error?.localizedDescription)
                }
            }
        }
 */
    
    }
    
    func signUp() {
       /*
        Auth.auth().createUser(withEmail: self.state.emailAddress, password: self.state.password) { authResult, error in
            if (authResult?.user != nil) {
                self.state = self.state.build { (builder) in
                    builder.state = AuthenticationState.Success.init()
                }
            } else {
                self.state = self.state.build { (builder) in
                    builder.state = AuthenticationState.Error.init(userEmail: "", userPassword: "", mode: self.state.authenticationMode, message: error?.localizedDescription)
                }
            }
        }
 */
    }
}
