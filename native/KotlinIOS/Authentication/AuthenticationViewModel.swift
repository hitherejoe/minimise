import Foundation
import SharedAuthentication

class AuthenticationViewModel: ObservableObject, AuthenticateView {
    
    @Published internal var state: AuthenticationState = AuthenticationState.Companion.init().initialise()
    private var authenticateUseCase: Authenticate
    
    init(authenticate: Authenticate) {
        self.authenticateUseCase = authenticate
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
            builder.loading = true
            builder.error = nil
        }
        /*
        backendService.signIn(emailAddress: self.state.emailAddress,
                              password: self.state.password) { (success) in
                                if (success) {
                                    self.state = self.state.build { (builder) in
                                        builder.success = true
                                    }
                                } else {
                                    self.state = self.state.build { (builder) in
                                        builder.error = "Error"
                                    }
                                }
        }
 */
    }
    
    func signUp() {
        self.state = self.state.build { (builder) in
            builder.loading = true
            builder.error = nil
        }
        /*
        backendService.signUp(emailAddress: self.state.emailAddress,
                              password: self.state.password) { (success) in
                                if (success) {
                                    self.state = self.state.build { (builder) in
                                        builder.success = true
                                    }
                                } else {
                                    self.state = self.state.build { (builder) in
                                         builder.error = "Error"
                                    }
                                }
        }
 */
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
