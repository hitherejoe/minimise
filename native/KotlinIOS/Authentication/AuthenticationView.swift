import Foundation
import SwiftUI
import Dashboard
import Common
import SharedAuthentication

public struct AuthenticationView: View {

    @ObservedObject var viewModel: AuthenticationViewModel
    var viewProvider: ScreenFactory
    @State var emailHasFocus: Bool = false
    @State var passwordHasFocus: Bool = false

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
                   emailHasFocus: self.$emailHasFocus,
                   passwordHasFocus: self.$passwordHasFocus,
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
            
        }.navigate(to: viewProvider.makeDashboardView(), when: Binding<Bool>(
                get: {
                    self.viewModel.state.success == true
                },
                set: { _ in
        }
        ))
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
        
        return LargeButton(title: label,
        backgroundColor: Color("secondary"),
        foregroundColor: Color.white, action: self.action)
        
    }
}

struct LargeButtonStyle: ButtonStyle {
    
    let backgroundColor: Color
    let foregroundColor: Color
    let isDisabled: Bool
    
    func makeBody(configuration: Self.Configuration) -> some View {
        let currentForegroundColor = isDisabled || configuration.isPressed ? foregroundColor.opacity(0.3) : foregroundColor
        return configuration.label
            .padding()
            .foregroundColor(currentForegroundColor)
            .background(isDisabled || configuration.isPressed ? backgroundColor.opacity(0.3) : backgroundColor)
            .cornerRadius(12)
            .overlay(
                RoundedRectangle(cornerRadius: 6)
                    .stroke(currentForegroundColor, lineWidth: 0)
        )
            .padding([.top, .bottom], 10)
    }
}

struct LargeButton: View {
    
    private static let buttonHorizontalMargins: CGFloat = 20
    
    var backgroundColor: Color
    var foregroundColor: Color
    
    private let title: String
    private let action: () -> Void
    
    // It would be nice to make this into a binding.
    private let disabled: Bool
    
    init(title: String,
         disabled: Bool = false,
         backgroundColor: Color = Color.green,
         foregroundColor: Color = Color.white,
         action: @escaping () -> Void) {
        self.backgroundColor = backgroundColor
        self.foregroundColor = foregroundColor
        self.title = title
        self.action = action
        self.disabled = disabled
    }
    
    var body: some View {
        HStack {
            Spacer(minLength: LargeButton.buttonHorizontalMargins)
            Button(action:self.action) {
                Text(self.title)
                    .frame(maxWidth:.infinity)
            }
            .buttonStyle(LargeButtonStyle(backgroundColor: backgroundColor,
                                          foregroundColor: foregroundColor,
                                          isDisabled: disabled))
                .disabled(self.disabled)
            Spacer(minLength: LargeButton.buttonHorizontalMargins)
        }
        .frame(maxWidth:.infinity)
    }
}

struct AuthenticationContent: View {

    let email: Binding<String>
    let password: Binding<String>
    let emailHasFocus: Binding<Bool>
    let passwordHasFocus: Binding<Bool>
    let authenticateButtonText: String
    let switchAuthenticationModeText: String
    let isSigningUp: Bool
    var action: () -> Void
    var switchAuthenticationAction: () -> Void

    init(email: Binding<String>,
         password: Binding<String>,
         emailHasFocus: Binding<Bool>,
         passwordHasFocus: Binding<Bool>,
         authenticateButtonText: String,
         switchAuthenticationModeText: String,
         isSigningUp: Bool,
         action: @escaping () -> Void,
         switchAuthenticationAction: @escaping () -> Void
    ) {
        self.email = email
        self.password = password
        self.emailHasFocus = emailHasFocus
        self.passwordHasFocus = passwordHasFocus
        self.authenticateButtonText = authenticateButtonText
        self.switchAuthenticationModeText = switchAuthenticationModeText
        self.isSigningUp = isSigningUp
        self.action = action
        self.switchAuthenticationAction = switchAuthenticationAction
    }

    public var body: some View {
        VStack {
            Text("Minimise")
                .foregroundColor(Color.white)

            Spacer()
            
            VStack {
            
                CustomTextField(placeHolder: "Email address", value: email, hasFocus: emailHasFocus, width: 2).padding(.top, 30)
                .padding(.bottom, 24)
            CustomTextField(placeHolder: "Password", value: password,
                            hasFocus: passwordHasFocus, width: 2).padding(.bottom, 30)
                
            }.background(Color("secondary"))
            .cornerRadius(8)
            
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

struct CustomTextField: View {
    var placeHolder: String
    @Binding var value: String
    @Binding var hasFocus: Bool


    var width: CGFloat

    var body: some View {
        VStack {
            TextField(self.placeHolder, text: $value, onEditingChanged: { (editingChanged) in
                if editingChanged {
                    self.hasFocus = true
                    print("TextField focused")
                } else {
                    self.hasFocus = false
                    print("TextField focus removed")
                }
            })
                .accentColor(hasFocus ? Color.white : Color.gray)
            .padding(.horizontal, 16)

            Rectangle().frame(height: self.width)
                .foregroundColor(hasFocus ? Color.white : Color.gray)
            .padding(.horizontal, 16)
        }
        
    }
}

extension View {

    /// Navigate to a new view.
    /// - Parameters:
    ///   - view: View to navigate to.
    ///   - binding: Only navigates when this condition is `true`.
    func navigate<SomeView: View>(to view: SomeView, when binding: Binding<Bool>) -> some View {
        modifier(NavigateModifier(destination: view, binding: binding))
    }
}


// MARK: - NavigateModifier
fileprivate struct NavigateModifier<SomeView: View>: ViewModifier {

    // MARK: Private properties
    fileprivate let destination: SomeView
    @Binding fileprivate var binding: Bool


    // MARK: - View body
    fileprivate func body(content: Content) -> some View {
        NavigationView {
            ZStack {
                content
                    .navigationBarTitle("")
                    .navigationBarHidden(true)
                NavigationLink(destination: destination
                    .navigationBarTitle("")
                    .navigationBarHidden(true),
                               isActive: $binding) {
                    EmptyView()
                }
            }
        }
    }
}

