/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Domain.Carport;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Sean
 */
public interface ICarportDesign {

    void genBeamCrossSuppBrackets() throws SQLException;

    void genCrossSupport() throws SQLException;

    void genGutter() throws SQLException;

    void genPostBeamBolts() throws SQLException;

    void genRoofPieces() throws SQLException;

    void genRoofScrews() throws SQLException;

    void genBracketScrews() throws SQLException;

    void genSupportBeams() throws SQLException;

    void genSupportPosts() throws SQLException;

    void genWindbreakEnds() throws SQLException;

    void genWindbreakSides() throws SQLException;

    void genWindbreakEndCover() throws SQLException;
    
    void genWindbreakSidesCover() throws SQLException;
    
    void initPartGeneration(Carport cp) throws Exception;
    
}
