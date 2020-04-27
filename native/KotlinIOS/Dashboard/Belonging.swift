//
//  Belonging.swift
//  Dashboard
//
//  Created by Joe Birch on 23/04/2020.
//  Copyright © 2020 Joe Birch. All rights reserved.
//

import Foundation

struct Belonging {
    
    var id: String
    var userId: String
    var name: String
    var category: String
    var reasonsNeeded: String
    var usageFrequency: String
    var storeName: String
    var isPending: Bool
    var isDeleted: Bool
  //  var pendingDate: Timestamp
  //  var purchaseDate: Timestamp
  //  var lastUsed: Timestamp
    
    var dictionary: [String: Any] {
       return [
         "id": id,
         "userId": userId,
         "name": name,
         "category": category,
         "reasonsNeeded": reasonsNeeded,
         "usageFrequency": usageFrequency,
         "storeName": storeName,
         "isPending": isPending,
         "isDeleted": isDeleted
      //   "pendingDate": pendingDate,
     //    "purchaseDate": purchaseDate,
    //     "lastUsed": lastUsed
       ]
     }
}
