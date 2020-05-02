//
//  Belonging.swift
//  Dashboard
//
//  Created by Joe Birch on 23/04/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//

import Foundation
import FirebaseFirestore // (1)
import FirebaseFirestoreSwift

public struct Belonging: Codable, Identifiable {
    
    @DocumentID public var id: String?
    public var userId: String
    public var name: String
    public var category: String
    public var reasonsNeeded: String
    public var usageFrequency: String
    public var storeName: String
    public var isPending: Bool
    public var isDeleted: Bool
    @ServerTimestamp public var pendingDate: Timestamp?
    @ServerTimestamp public var purchaseDate: Timestamp?
    @ServerTimestamp public var lastUsed: Timestamp?
}
