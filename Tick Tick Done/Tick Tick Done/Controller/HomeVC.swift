//
//  HomeVC.swift
//  Tick Tick Done
//
//  Created by CP on 24/12/24.
//

import Foundation
import UIKit

class HomeVC: UIViewController {
    
    //MARK: - Outlets & Variables -
    
    @IBOutlet weak var lblTitle: UILabel!
    
    //MARK: - Life cycle methods -
    
    override func viewDidLoad() {
        super.viewDidLoad()
        print("View loaded home")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        print("View appeared home")
    }

    //MARK: - Custom methods -
    
    //MARK: - Action methods -
}
