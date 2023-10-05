package dev.sterner.geocluster.api.deposits;

import net.minecraft.util.Identifier;

import java.util.ArrayList;

public interface IChunk {
    ArrayList<Identifier> getDeposits();
    void addDeposit(Identifier deposit);
}
