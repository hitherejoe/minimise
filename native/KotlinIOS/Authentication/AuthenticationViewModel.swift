import Foundation
import SharedAuthentication

class AuthenticationViewModel: ObservableObject, AuthenticateView {
    
    @Published internal var state: AuthenticationState = AuthenticationState.Idle(userEmail: "", userPassword: "", mode: AuthenticateMode.SignUp.init())
    private var authenticateUser: Authenticate!
    
    init(authenticate : Authenticate) {
        self.authenticateUser = authenticate
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
        authenticateUser?.run(params:
        Authenticate.ParamsCompanion().forSignIn(apiKey: "AIzaSyBFLpvP6vOjrl_5s_R45E6s33FOFg6y5wQ", emailAddress: self.state.emailAddress, password: self.state.password)) { (result) in
                if (result.token != nil) {
                    self.state = self.state.build { (builder) in
                        builder.state = AuthenticationState.Success.init()
                    }
                } else {
                    self.state = self.state.build { (builder) in
                        builder.state = AuthenticationState.Error.init(userEmail: "", userPassword: "", mode: self.state.authenticationMode, message: result.message)
                    }
                }
        }
    }
    
    func signUp() {
        self.state = self.state.build { (builder) in
            builder.state = AuthenticationState.Loading.init(userEmail: "", userPassword: "", mode: self.state.authenticationMode)
        }
        authenticateUser?.run(params:
            Authenticate.ParamsCompanion().forSignUp(apiKey: "AIzaSyBFLpvP6vOjrl_5s_R45E6s33FOFg6y5wQ", emailAddress: self.state.emailAddress, password: self.state.password)) { (result) in
                if (result.token != nil) {
                    self.state = self.state.build { (builder) in
                        builder.state = AuthenticationState.Success.init()
                    }
                } else {
                   self.state = self.state.build { (builder) in
                       builder.state = AuthenticationState.Error.init(userEmail: "", userPassword: "", mode: self.state.authenticationMode, message: result.message)
                   }
                }
        }
    }
}
