//
//  AuthenticationViewModelTest.swift
//  AuthenticationTests
//
//  Created by Joe Birch on 21/03/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//

import XCTest
@testable import SharedAuthentication
@testable import Authentication

class AuthenticationViewModelTest: XCTestCase {
    
    var viewModel: AuthenticationViewModel!

    override func setUp() {
        super.setUp()
        self.viewModel = AuthenticationViewModel(authenticate: nil)
    }

    override func tearDown() { }

    func testSetEmailAddressEmitsExpectedEmailState() {
        let email = "joe@birch.com"
        viewModel.setEmailAddress(emailAddress: email)
        XCTAssertTrue(viewModel.state.emailAddress == email)
    }
    
    func testSetPasswordEmitsExpectedPasswordState() {
        let password = "my_password"
        viewModel.setPassword(password: password)
        XCTAssertTrue(viewModel.state.password == password)
    }
}
