import Foundation
import SharedCode

class AuthenticationViewModel: ObservableObject, AuthenticateView {
    
    @Published private(set) var state: AuthenticationState?
    weak var authenticate: Authenticate?
    
    init(authenticate : Authenticate) {
        self.authenticate = authenticate
    }
    
    func signIn(emailAddress: String, password: String) {
        self.state = AuthenticationState.Loading()
        authenticate?.run(params:
            Authenticate.ParamsCompanion().forSignIn(emailAddress: emailAddress, password: password)) { (AuthenticationModel) in
                if (AuthenticationModel.success) {
                    self.state = AuthenticationState.Success()
                } else if (!AuthenticationModel.success) {
                    self.state = AuthenticationState.Failure(error: AuthenticationModel.message)
                }
        }
    }
    
    func signUp(emailAddress: String, password: String) {
        self.state = AuthenticationState.Loading()
        authenticate?.run(params:
            Authenticate.ParamsCompanion().forSignUp(emailAddress: emailAddress, password: password)) { (AuthenticationModel) in
                if (AuthenticationModel.success) {
                    self.state = AuthenticationState.Success()
                } else if (!AuthenticationModel.success) {
                    self.state = AuthenticationState.Failure(error: AuthenticationModel.message)
                }
        }
    }
}
