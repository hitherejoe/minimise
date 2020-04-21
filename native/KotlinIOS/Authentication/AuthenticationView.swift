import Foundation
import SwiftUI
import SharedAuthentication

public struct AuthenticationView: View {

    @ObservedObject var viewModel = AuthenticationViewModel(authenticate: Authenticate())

    public init() {
    }

    public func authenticateButtonText() -> String {
        if (self.viewModel.state.authenticationMode.isKind(of: AuthenticateMode.SignUp.self)) {
            return "Sign Up"
        } else {
            return "Sign In"
        }
    }

    public func switchAuthenticationModeText() -> String {
        if (self.viewModel.state.authenticationMode.isKind(of: AuthenticateMode.SignIn.self)) {
            return "Don't have an account?"
        } else {
            return "Already have an account?"
        }
    }

    public var body: some View {
        return ZStack {
            Rectangle().foregroundColor(Color("primary"))
                    .edgesIgnoringSafeArea(.all)

            AuthenticationContent(
                email: Binding<String>(
                        get: {
                            self.viewModel.state.emailAddress
                        },
                        set: { emailAddress in
                            self.viewModel.setEmailAddress(emailAddress: emailAddress)
                        }
                ), password: Binding<String>(
                        get: {
                            self.viewModel.state.password
                        },
                        set: { password in
                            self.viewModel.setPassword(password: password)
                        }
                ),
                    authenticateButtonText: authenticateButtonText(),
                    switchAuthenticationModeText: switchAuthenticationModeText(),
                    isSigningUp: self.viewModel.state.authenticationMode.isKind(of: AuthenticateMode.SignUp.self),
                    action: {
                        self.viewModel.authenticate()
                    }, switchAuthenticationAction: {
                        self.viewModel.toggleAuthenticationMode()
            }).alert(isPresented: Binding<Bool>(
                    get: {
                        self.viewModel.state.errorMessage != nil
                    },
                    set: { _ in
                      //  self.viewModel.state = AuthenticationState.Idle()
                    }
            ), content: {
                Alert(title: Text("Whoops!"), message: Text(self.viewModel.state.errorMessage ?? ""), dismissButton: .default(Text("Got it!")))
            }).padding(.all, 24.0)
        }
    }
}

struct ProgressIndicator: View {

    let loading: Bool

    init(loading: Bool) {
        self.loading = loading
    }

    public var body: some View {
        ZStack {
            Circle()
                    .trim(from: 0.5, to: 1)
                    .stroke(Color.blue, lineWidth: 4)
                    .frame(width: 50)
                    .rotationEffect(.degrees(loading ? 0 : -360), anchor: .center)
                    .animation(Animation.linear(duration: 1).repeatForever(autoreverses: false))
        }
    }
}

struct ConnectButton: View {

    var label: String
    var action: () -> Void

    init(
            label: String,
            action: @escaping () -> Void
    ) {
        self.label = label
        self.action = action
    }

    var body: some View {
        return Button(action: self.action) {
            HStack {
                Spacer()
                Text(label)
                Spacer()
            }
        }
                .padding(.vertical, 10.0)
                .background(true ? Color.blue : Color.gray)
                .foregroundColor(Color.white)
                .padding(.all, 20)
    }
}

struct AuthenticationContent: View {

    let email: Binding<String>
    let password: Binding<String>
    let authenticateButtonText: String
    let switchAuthenticationModeText: String
    let isSigningUp: Bool
    var action: () -> Void
    var switchAuthenticationAction: () -> Void

    init(email: Binding<String>,
         password: Binding<String>,
         authenticateButtonText: String,
         switchAuthenticationModeText: String,
         isSigningUp: Bool,
         action: @escaping () -> Void,
         switchAuthenticationAction: @escaping () -> Void
    ) {
        self.email = email
        self.password = password
        self.authenticateButtonText = authenticateButtonText
        self.switchAuthenticationModeText = switchAuthenticationModeText
        self.isSigningUp = isSigningUp
        self.action = action
        self.switchAuthenticationAction = switchAuthenticationAction
    }

    public var body: some View {
        VStack {
            Text("Minimise")

            Spacer()

            TextField("Email", text: email)
                    .padding(.all, 10.0)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
            TextField("Password", text: password)
                    .padding(.all, 10.0)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
            
            if (isSigningUp) {
                Button(action: self.action) {
                    Text("Forgotten your password?")
                    .padding(.all, 10.0)
                }
                
            }

            Spacer()

            ConnectButton(label:self.authenticateButtonText, action: self.action)

            ConnectButton(label:self.switchAuthenticationModeText, action: self.switchAuthenticationAction)
        }
    }
}
