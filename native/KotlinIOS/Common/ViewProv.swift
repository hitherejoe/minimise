//
//  ViewProv.swift
//  KotlinIOS
//
//  Created by Joe Birch on 27/04/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//

import Foundation
import SwiftUI

public protocol ViewProv {
    func makeDashboardView() -> AnyView
}
