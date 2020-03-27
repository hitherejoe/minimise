import Foundation
import SharedAuthentication

class AuthenticationViewModel: ObservableObject, AuthenticateView {
    
    @Published internal var state: AuthenticationState = AuthenticationState.init(emailAddress: "", password: "", authenticationMode: AuthenticateMode.SignIn.init(), isLoading: false, success: false, errorMessage: nil)
    weak var authenticateUser: Authenticate!
    
    init(authenticate : Authenticate) {
        self.authenticateUser = authenticate
    }
    
    func setEmailAddress(emailAddress: String) {
        self.state = self.state.build { (builder) in
            builder.emailAddress = emailAddress
        }
    }
    
    func setPassword(password: String) {
        self.state = self.state.build { (builder) in
            builder.password = password
        }
    }
    
    func toggleAuthenticationMode() {
        if (state.authenticationMode.isKind(of: AuthenticateMode.SignIn.self)) {
            self.state = self.state.build { (builder) in
                builder.authenticateMode = AuthenticateMode.SignUp.init()
            }
        } else {
            self.state = self.state.build { (builder) in
                builder.authenticateMode = AuthenticateMode.SignIn.init()
            }
        }
    }
    
    func authenticate() {
        if (state.authenticationMode.isKind(of: AuthenticateMode.SignIn.self)) {
            signUp()
        } else {
            signIn()
        }
    }
    
    func signIn() {
        self.state = self.state.build { (builder) in
            builder.isLoading = true
            builder.errorMessage = nil
        }
        authenticateUser?.run(params:
        Authenticate.ParamsCompanion().forSignIn(apiKey: "AIzaSyBFLpvP6vOjrl_5s_R45E6s33FOFg6y5wQ", emailAddress: self.state.emailAddress, password: self.state.password)) { (result) in
                if (result.token != nil) {
                    self.state = self.state.build { (builder) in
                        builder.success = true
                        builder.isLoading = false
                    }
                } else {
                    self.state = self.state.build { (builder) in
                        builder.isLoading = false
                        builder.errorMessage = result.message
                    }
                }
        }
    }
    
    func signUp() {
        self.state = self.state.build { (builder) in
            builder.isLoading = true
            builder.errorMessage = nil
        }
        authenticateUser?.run(params:
            Authenticate.ParamsCompanion().forSignUp(apiKey: "AIzaSyBFLpvP6vOjrl_5s_R45E6s33FOFg6y5wQ", emailAddress: self.state.emailAddress, password: self.state.password)) { (result) in
                if (result.token != nil) {
                    self.state = self.state.build { (builder) in
                        builder.isLoading = false
                        builder.success = true
                    }
                } else {
                   self.state = self.state.build { (builder) in
                       builder.isLoading = false
                       builder.errorMessage = result.message
                   }
                }
        }
    }
}
