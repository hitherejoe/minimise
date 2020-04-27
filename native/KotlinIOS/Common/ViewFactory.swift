//
//  ViewFactory.swift
//  Common
//
//  Created by Joe Birch on 26/04/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//

import Foundation
import SwiftUI

public protocol ViewFactory {
    func make() -> AnyView
}
