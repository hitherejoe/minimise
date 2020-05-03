//
//  CreationViewFactory.swift
//  Creation
//
//  Created by Joe Birch on 02/05/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//

import Foundation
import SwiftUI
import Common

public struct CreationViewFactory : ViewFactory {
    
    public init() {
        
    }

    public func make() -> AnyView {
        return AnyView(CreationView())
    }
}
