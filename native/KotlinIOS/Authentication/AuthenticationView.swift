import Foundation
import SwiftUI
import SharedCode

public struct AuthenticationView: View {
    
    public init() {}
    
    @ObservedObject var  viewModel = AuthenticationViewModel(authenticate: Authenticate())

    public var body: some View {
    ZStack {
        Rectangle().foregroundColor(Color("primary"))
            .edgesIgnoringSafeArea(.all)
        VStack {
            
            
            Button("Authenticate") {
                self.viewModel.signUp(emailAddress: "joebirch@hotmail.com", password: "password")
               }
        }
    }
  }
}
