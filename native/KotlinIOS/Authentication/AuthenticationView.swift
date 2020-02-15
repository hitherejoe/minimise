import Foundation
import SwiftUI
import SharedAuthentication

public struct AuthenticationView: View {

    @ObservedObject var viewModel = AuthenticationViewModel(authenticate: Authenticate())
    @State private var email = ""
    @State private var password = ""
    @State private var authenticateMode: AuthenticateMode = AuthenticateMode.SignUp.init()

    public init() {
    }

    public func authenticateButtonText() -> String {
        if (authenticateMode.isKind(of: AuthenticateMode.SignUp.self)) {
            return "Sign Up"
        } else {
            return "Sign In"
        }
    }

    public func switchAuthenticationModeText() -> String {
        if (authenticateMode.isKind(of: AuthenticateMode.SignIn.self)) {
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
                    email: self.$email, password: self.$password,
                    authenticateButtonText: authenticateButtonText(),
                    switchAuthenticationModeText: switchAuthenticationModeText(),
                    action: {
                        if (self.authenticateMode.isKind(of: AuthenticateMode.SignUp.self)) {
                            self.viewModel.signUp(emailAddress: self.email, password: self.password)
                        } else {
                            self.viewModel.signIn(emailAddress: self.email, password: self.password)
                        }
                    }, switchAuthenticationAction: {
                if (self.authenticateMode.isKind(of: AuthenticateMode.SignUp.self)) {
                    self.authenticateMode = AuthenticateMode.SignIn.init()
                } else {
                    self.authenticateMode = AuthenticateMode.SignUp.init()
                }
            }).alert(isPresented: Binding<Bool>(
                    get: { self.viewModel.state.isKind(of: AuthenticationState.Failure.self) },
                    set: { _ in self.viewModel.state = AuthenticationState.Idle() }
            ), content: {
                Alert(title: Text("Whoops!"), message: Text("There was a problem authenticating with those credenitals. Please try again."), dismissButton: .default(Text("Got it!")))
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
    var action: () -> Void
    var switchAuthenticationAction: () -> Void

    init(email: Binding<String>,
         password: Binding<String>,
         authenticateButtonText: String,
         switchAuthenticationModeText: String,
         action: @escaping () -> Void,
         switchAuthenticationAction: @escaping () -> Void
    ) {
        self.email = email
        self.password = password
        self.authenticateButtonText = authenticateButtonText
        self.switchAuthenticationModeText = switchAuthenticationModeText
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
            SecureField("Password", text: password)
                    .padding(.all, 10.0)
                    .textFieldStyle(RoundedBorderTextFieldStyle())

            Spacer()

            ConnectButton(label:self.authenticateButtonText, action: self.action)

            ConnectButton(label:self.switchAuthenticationModeText, action: self.switchAuthenticationAction)
        }
    }
}
